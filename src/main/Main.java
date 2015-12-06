package main;
import java.awt.EventQueue;
import java.io.File;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.json.*;

public class Main {

	
	public static void main(String[] args) throws Exception{  
		HttpRequest httpAgent = new HttpRequest();
		RandomAgent randomAgent = new RandomAgent(httpAgent);
// parameters -----------------------------------------------------------------------------------------------------------------------------------
		
		//parameters of posting hits.
		String hit_type = "text";
		String hit_title = "trial3";
		String hit_intro = "this is a trial question.";
		int credit = 10;
		int num_ans = 5;
		
		//randomly pick a requester from the user list
		int requester_id = randomAgent.getRandomUser();
		//randomly pick a requester from the predefined location list
	    int hit_location_id = randomAgent.getRandomLocation();
	    
		
		//****** please customize this default directory of attachments for answers.****** 
		String photo_directory = "/Users/robin/Desktop"; 
		
//posting tasks---------------------------------------------------------------------------------------------------------------------------------------
		
		//transform the parameters into json format
		String hit_param = "{\"type\":\""+ hit_type +"\""+ ",\"title\":" + "\""+hit_title+ "\""+",\"description\":\""+ hit_intro + "\",\"credit\":"+ credit +
	    		",\"required_answer_count\":"+ num_ans + ",\"requester_id\":"+ requester_id + ",\"location_id\":" + hit_location_id +"} ";
		
		//posting a task and obtain its id
		JSONObject hit = httpAgent.postRestful("hit", hit_param);
		System.out.println("HIT posted: " + hit);
		int hit_id = hit.getInt("id");
		
//posting answers---------------------------------------------------------------------------------------------------------------------------------------
		
		//obtain the brief and attachment of the answers
		String ans_brief = JOptionPane.showInputDialog("Please type in content of answers(press cancel to stop posting answers).");	
		while(ans_brief != null){
			//picking photos
			String attachment = null;
			FileAgent agent = new FileAgent(photo_directory);
			String path = agent.getFilePath();
	        if(path != null) 
	        { 
	        	File file = new File(path);
	            String response = httpAgent.postFile(file);
	            String uploaded_fileName = new String((new JSONObject(response)).getString("filename"));
	            attachment = new String("{\"name\":" + "\"" + file.getName() + "\"" + ",\"type\":" + "\"image\"" +",\"value\":" +
	            		"\"" + uploaded_fileName + "\"" + "}");
	            attachment = ",\"attachment\":" + attachment;
	            System.out.println("attachment uploaded: " + attachment);
	        }
	        
			//randomly pick an worker.
	        int worker_id = randomAgent.getRandomUser();
			while(worker_id == requester_id){
				worker_id = randomAgent.getRandomUser();
			}
			//randomly picking a location from the predefined location list
			 int answer_location_id = randomAgent.getRandomLocation();
			
			//specifying ans_type
			String ans_type = "text";
			if(path != null){
				ans_type = "image";
			}
			
			
//			
//			//posting the attachment
//			JSONObject uploaded_pic = httpAgent.postRestful("attachment", attachment);
//			int pic_id = uploaded_pic.getInt("id");
			
			//transform the answer parameters into json format
//			 String ans_param = "{\"type\":\""+ ans_type + "\"" +",\"brief\":\"" + ans_brief + "\"" +",\"hit_id\":"+ hit_id + ", \"worker_id\":"+ worker_id +
//					 ans_location + (attachment!=null?attachment:"")  +"}";	
			
			 String ans_param = "{\"type\":\""+ ans_type + "\"" +",\"brief\":\"" + ans_brief + "\"" +",\"hit_id\":"+ hit_id + ", \"worker_id\":"+ worker_id +
					 ",\"location_id\":" + answer_location_id + (attachment!=null?attachment:"") +"}";		

			 //posting answers
			 JSONObject ans = httpAgent.postRestful("answer", ans_param);
			 System.out.println("Answer posted: " + ans);
			 ans_brief = JOptionPane.showInputDialog("Please type in content of answers(press cancel to stop posting answers).");	
		}
		
		System.out.println("Process ended!");


        
    } 
}

//String param = "{\"type\":\"text\",\"brief\":\"this is a simple text question\",\"credit\":10,"		
//+ "\"status\":\"open\",\"required_answer_count\":3,\"requester_id\":1,\"location_id\":1} ";
//String param = "{ \"brief\":\"this is a simple answer posted by robin\", \"hit_id\":6, \"worker_id\":1,\"location_id\":1}";	
//String  param = "{ \"name\":\"robin\", \"parent_id\":1, \"type\":\"string\",\"value\":\"nothing\"}";	
//httpAgent.postRestful("attachment", param);
//httpAgent.getRestful("user", null);
//httpAgent.getDefinitionList();
//httpAgent.getDefinition("Attachment");
//httpAgent.getUserDefinition();		
//httpAgent.getHit();
//httpAgent.getLocationList();


