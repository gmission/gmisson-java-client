package test;

import org.json.JSONObject;

import main.HttpRequest;
import main.RandomAgent;
import java.io.File;

import org.json.JSONObject;

public class test {
	public static void main(String[] args) throws Exception {
//		  // TODO Auto-generated method st
//		 HttpRequest httpAgent = new HttpRequest();
//		 JSONObject obj = httpAgent.getRestful("user", null);
//			 
//		 System.out.println(obj);
//		String hit_type = "text";
//		String title = "test";
//		String hit_brief = "this is a testing question.";
//		int credit = 10;
//		int num_ans = 5;
//		String coordinate = "{\"longitude\":\"100.5\",\"latitude\":\"120.5\","+ "\"altitude\":\"" + 0 +"\"}";
//		String location =  "{\"name\":"+ "\"test\"" + ",\"coordinate\":"+ coordinate +  "}";
//		String hit_location = ",\"location\":" + location; 
//		System.out.println(location);
//		JSONObject obj = httpAgent.postRestful("location", location);
//		System.out.println(obj);
//		int location_id = obj.getInt("id");
//		String hit_param = "{\"type\":\""+ hit_type +"\""+ ",\"title\":" + "\""+title+ "\""+",\"description\":\""+ hit_brief + "\",\"credit\":"+ credit +
//	    		",\"required_answer_count\":"+ num_ans + ",\"requester_id\":"+ 1 + ",\"location_id\":" + 1 +"} ";
//		System.out.println(hit_param);
//		JSONObject obj = httpAgent.postRestful("hit", hit_param);
//		System.out.println(obj);
//		 
//		 
//		 String photo_directory = "/Users/robin/Desktop"; 
//		    String attachment = null;
//			FileAgent agent = new FileAgent(photo_directory);
//			String path = agent.getFilePath();
//	        if(path != null) 
//	        { 
//	        	File file = new File(path);
//	            String response = httpAgent.postFile(file);
//	            String uploaded_fileName = new String((new JSONObject(response)).getString("filename"));
//	            attachment = new String("{\"name\":" + "\"" + file.getName() + "\"" + ",\"type\":" + "\"image\"" +",\"value\":" +
//	            		"\"" + uploaded_fileName + "\"" + "}");
//	            attachment = ",\"attachment\":" + attachment;
//	            System.out.println(attachment);
//	        }
//	        
//	        JSONObject uploaded_pic = httpAgent.postRestful("attachment", attachment);
//	        System.out.println(uploaded_pic);
//			int pic_id = uploaded_pic.getInt("id");
//		 
//		 int pic_id = 1;
//			String ans_type = "image";
//			String ans_brief =  "testing answer";
//			int hit_id = 1;
//			int worker_id = 1;
//			int answer_location_id = 1;
//			String answer_location = ",\"location\":" + location; 
//			 String ans_param = "{\"type\":\""+ ans_type + "\"" +",\"brief\":\"" + ans_brief + "\"" +",\"hit_id\":"+ hit_id + ", \"worker_id\":"+ worker_id +
//					 ",\"location_id\":" + 1 + (attachment!=null?attachment:"") +"}";	
//			 
//			 System.out.println(ans_param);
//			 //posting answers
//			 JSONObject ans = httpAgent.postRestful("answer", ans_param);
//			 System.out.println("Answer posted: " + ans);
//	   
//     	
//	    httpAgent.getDefinitionList();
//	    httpAgent.getDefinition("hit");
//	    httpAgent.getDefinition("location");
//	    httpAgent.getDefinition("attachment");
//	    httpAgent.getDefinition("answer");
//	    String location =  "{\"name\":"+ "\"test\"" +",\"longitude\":\"100.5\",\"latitude\":\"120.5\","+ "\"bound\":" + bound +"}";
//	    String hit_param = "{\"type\":\""+ hit_type + "\",\"description\":\""+ hit_brief + "\",\"credit\":"+ credit +
//	    		",\"required_answer_count\":"+ num_ans + ",\"requester_id\":"+ 1 + ",\"location_id\":"+ 1 +"} ";
//	    System.out.p                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            rintln(hit_param);
//		JSONObject obj = httpAgent.postRestful("hit", hit_param);
//		System.out.println(obj);
//		httpAgent.getRestful("user", null);
//		httpAgent.getDefinitionList();
//		httpAgent.getDefinition("Attachment");
//		httpAgent.getUserDefinition();		
//		httpAgent.getHit();
//		System.out.println(httpAgent.getUserDefinition());
//		System.out.println(httpAgent.getRestful("hit", null));
		
	  }
}
