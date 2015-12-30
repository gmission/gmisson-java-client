package main;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.json.*;

public class Main {
	static String HIT_TYPE_TEXT = "text";
	static String HIT_TYPE_IMAGE = "image";
	static String HIT_TYPE_SELECTION = "selection";
	
	static String ANSWER_TYPE_TEXT = "text";
	static String ANSWER_TYPE_IMAGE = "image";
	static String ANSWER_TYPE_SELECTION = "selection";
	
	static HttpRequest httpAgent;
	static RandomAgent randomAgent;
	
	public static void main(String[] args) throws Exception{  
		httpAgent = new HttpRequest();
		randomAgent = new RandomAgent(httpAgent);
// parameters -----------------------------------------------------------------------------------------------------------------------------------
			
		//parameters of posting hits.
		String hit_type = ANSWER_TYPE_TEXT;
		String hit_title = "trial4";
		String hit_intro = "Tested selection question.";
		int credit = 10;
		int num_ans = 5;
		//for type SELECTION tasks
		int min_select_cnt = 2;
		int max_select_cnt = 3;
		
		if( hit_type != HIT_TYPE_SELECTION){
			 min_select_cnt = 1;
			 max_select_cnt = 1;
		}
		
		//For multi-choice selection tasks
		String[] choices = new String[]{"very good","good","soso","bad"};
		ArrayList<Integer> choice_ids = new ArrayList<Integer>();
		
		//randomly pick a requester from the user list
		int requester_id = randomAgent.getRandomUser();
		//randomly pick a requester from the predefined location list
	    int hit_location_id = randomAgent.getRandomLocation();
	    
		
		//****** please customize this default directory of attachments for answers.****** 
		String photo_directory = "/Users/robin/Desktop"; 
		
		//parameters of campaigns, name of a campaign should be unique.
		Boolean postCam = false;
		String cam_title = new Date().toString();
		
		
//posting campaigns-----------------------------------------------------------------------------------------------------------------------------------
		int cam_id = 0;
		if(postCam){
			String cam_param  = new JSONObject().append("title", cam_title).toString();
			JSONObject campaign = httpAgent.postRestful("campaign", cam_param);
			System.out.println("Campaign posted: " + campaign);
		    cam_id = campaign.getInt("id");
		}

//posting tasks---------------------------------------------------------------------------------------------------------------------------------------
		
		//transform the parameters into json format
		JSONObject hit_param  = new JSONObject().append("title", hit_title).append("type", hit_type).append("description", hit_intro).append("credit", credit)
				.append("required_answer_count", num_ans).append("requester_id", requester_id).append("location_id", hit_location_id)
				.append("min_selection_count", min_select_cnt).append("max_selection_count", max_select_cnt);
				
		if(postCam){
			hit_param.append("campaign_id", cam_id);
		}
		
		//posting a task and obtain its id
		JSONObject hit = httpAgent.postRestful("hit", hit_param.toString());
		System.out.println("HIT posted: " + hit);
		int hit_id = hit.getInt("id");
		
		//post choices for selection tasks
		for(int i = 0 ; i < choices.length; i++){
			String chooice_param = new JSONObject().append("hit_id", hit_id).append("brief", choices[i]).toString();
			JSONObject choice = httpAgent.postRestful("selection", chooice_param);
			choice_ids.add(choice.getInt("id"));
			System.out.println("Choice posted: " + choice );
		}
			

//posting answers---------------------------------------------------------------------------------------------------------------------------------------
		
		//obtain answers
		if(hit_type == HIT_TYPE_SELECTION)
			ansSelect(hit_intro, choices, hit_id,  requester_id, choice_ids,  min_select_cnt,  max_select_cnt);			
		else
			ansText( photo_directory,  hit_id,  requester_id);
		System.out.println("Process ended!"); 
    } 
	
	
	
	
	
	public static void ansText(String photo_directory, int hit_id, int requester_id) throws Exception{
		//obtain the brief and attachment of the answers
		String ans_brief = JOptionPane.showInputDialog("Please type in content of answers(press cancel to stop posting answers).");	
		while(ans_brief != null){
			//picking photos
			String attachment = null;
			FileAgent agent = new FileAgent(photo_directory);
			String path = agent.getFilePath();
			
			
			//posting attachment and obtain its id
			int attachment_id = 0;
	        if(path != null) 
	        { 
	        	File file = new File(path);
	            String str_response = httpAgent.postFile(file);
	            if(str_response.equals("error")){
	            	System.out.println("Error occurs when posting the selecred file");
	            	continue;
	            }
	            JSONObject response = new JSONObject(str_response);
	            String uploaded_fileName = new String(response.getString("filename"));
	            
	            JSONObject attach_param = new JSONObject().append("name", file.getName()).append("type", "image").append("value", uploaded_fileName);
	            
	            JSONObject uploaded_pic = httpAgent.postRestful("attachment", attach_param.toString());
				System.out.println("attachment posted: " + uploaded_pic.toString());
				attachment_id = uploaded_pic.getInt("id");
	        }
	        
			//randomly pick an worker.
	        int worker_id = randomAgent.getRandomUser();
			while(worker_id == requester_id){
				worker_id = randomAgent.getRandomUser();
			}
			//randomly picking a location from the predefined location list
			 int answer_location_id = randomAgent.getRandomLocation();

			 
			 JSONObject ans_param = new JSONObject();
			 
			//specifying ans_type
			String ans_type = ANSWER_TYPE_TEXT;
			if(path != null){
				ans_type = ANSWER_TYPE_IMAGE;
				ans_param.append("attachment_id", attachment_id);
			}
			
			 ans_param.append("type", ans_type).append("brief", ans_brief).append("hit_id", hit_id)
					.append("location_id", answer_location_id).append("worker_id", worker_id);

			 //posting answers
			 JSONObject ans = httpAgent.postRestful("answer", ans_param.toString());
			 System.out.println("Answer posted: " + ans);
			 ans_brief = JOptionPane.showInputDialog("Please type in content of answers(press cancel to stop posting answers).");	
		}
	}
	
	
	//to be tested
	public static void ansSelect(String hit_intro, String[] choices, int hit_id,  int requester_id, ArrayList<Integer> choice_ids, int min_select_cnt, int max_select_cnt){
		int num_choices = choice_ids.size();
		while(true){
			System.out.println(hit_intro);
			ArrayList<Integer> selected = new ArrayList<Integer>();
			for(int i = 0 ; i < choices.length; i++){
				System.out.print((i+1) + ": "+ choices[i] + "   ");
			}
			System.out.println("Input "+ min_select_cnt + "~" + max_select_cnt +" choices in numbers, "
					+ "separated by blanks(input nothing for stop)");
			String input = new Scanner(System.in).nextLine();
			String[] chars = input.split("\\s+");
			

			if(chars[0].equals(""))
				break;
			int k = 0;
			try{
				while(k < chars.length){
					if(!chars[k].equals("")){
						int choice = Integer.parseInt(chars[k]);
						if(choice < 1 | choice > num_choices){
							throw new Exception("Not all numbers are in valid range.");
						}
						selected.add(choice);
					}
					k++;
				}
				int ans_cnt = selected.size();
				if(ans_cnt < min_select_cnt | ans_cnt > max_select_cnt){
					throw new Exception("The number of your choices is either too less or too much!"); 
				}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage() + " please input your answers again!");
				continue;
			}
			
			//randomly pick an worker.
	        int worker_id = randomAgent.getRandomUser();
			while(worker_id == requester_id){
				worker_id = randomAgent.getRandomUser();
			}
			
			//randomly picking a location from the predefined location list
			 int answer_location_id = randomAgent.getRandomLocation();
			
			System.out.print("Answers posted:  ");
			for(int i = 0; i < selected.size();i++){
				int ans_id = choice_ids.get(selected.get(i) - 1);				
				// post the selected choices.
				JSONObject ans_param = new JSONObject();
				ans_param.append("type", ANSWER_TYPE_SELECTION).append("brief", ans_id).append("hit_id", hit_id)
				.append("location_id", answer_location_id).append("worker_id", worker_id);
				JSONObject ans = httpAgent.postRestful("answer", ans_param.toString());
				 System.out.println(ans);
			}
		}
	}
}


