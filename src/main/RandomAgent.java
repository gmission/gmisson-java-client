package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;


public class RandomAgent {
	//list of registeres users(serve as requesters and workers)
	private ArrayList<Integer> users_id = new ArrayList<Integer>();
	//list of posted locations
	private ArrayList<Integer> locations_id = new ArrayList<Integer>();
	
	private HttpRequest httpAgent = null;
	
	
public RandomAgent(HttpRequest agent){
	  HttpRequest httpAgent = agent;
	  File usr_file = new File("user.txt");
	  File loca_file = new File("location.txt");
	  if(usr_file.exists()){
		   //If already posted 
		  try {
			     //read users' ids
				 FileReader reader = new FileReader(usr_file);
				 char[] temp = new char[10000];  
				 reader.read(temp); 
				 reader.close();
				 String temp2 = new String(temp);
				 String[] chars = temp2.split(" ");
				 //the last character in chars is ""(empty)
				 for(int i = 0; i < chars.length - 1; i++){
						users_id.add(Integer.parseInt(chars[i]));		
						
				  }
				//read locations' ids
				 temp = new char[10000];  
				 reader = new FileReader(loca_file);
				 reader.read(temp); 
				 reader.close();
				  temp2 = new String(temp);
				  chars = temp2.split(" ");
				  for(int i = 0; i < chars.length - 1 ; i++){
						locations_id.add(Integer.parseInt(chars[i]));		
				  }
			} catch (Exception e) {
				e.printStackTrace();
			}
	  }
	  else{
			  try {
					usr_file.createNewFile();
					loca_file.createNewFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
		  
		  ArrayList<String> locations = new ArrayList<String>();
		  ArrayList<String> users = new ArrayList<String>();
	//******set the parameters of posted locations here********
	      locations.add( "{\"name\":\"HKUST\",\"coordinate\":{\"longitude\":\"114.265465\",\"latitude\":\"22.3364\",\"altitude\":\"0.0\"}}");
		  locations.add( "{\"name\":\"Library\",\"coordinate\":{\"longitude\":\"114.2500\",\"latitude\":\"22.3333\",\"altitude\":\"0.0\"}}");
		  locations.add( "{\"name\":\"Playground\",\"coordinate\":{\"longitude\":\"114.2667\",\"latitude\":\"22.3333\",\"altitude\":\"0.0\"}}");
		  locations.add( "{\"name\":\"Academic Building\",\"coordinate\":{\"longitude\":\"114.2500\",\"latitude\":\"22.3333\",\"altitude\":\"0.0\"}}");
		  locations.add( "{\"name\":\"University Apartment\",\"coordinate\":{\"longitude\":\"114.2500\",\"latitude\":\"22.3333\",\"altitude\":\"0.0\"}}");
		  locations.add( "{\"name\":\"Swimming Pool\",\"coordinate\":{\"longitude\":\"114.2667\",\"latitude\":\"22.3333\",\"altitude\":\"0.0\"}}");
		  locations.add( "{\"name\":\"UG Hall\",\"coordinate\":{\"longitude\":\"114.2500\",\"latitude\":\"22.3333\",\"altitude\":\"0.0\"}}");
		  locations.add( "{\"name\":\"LSK Business Building\",\"coordinate\":{\"longitude\":\"114.2500\",\"latitude\":\"22.3167\",\"altitude\":\"0.0\"}}");
	//******set the parameters of posted users here********
		  users.add("{ \"username\": \"user1\",\"password\": \"user1\",\"email\":\"user1@gmail.com\" }");
		  users.add("{ \"username\": \"user2\",\"password\": \"user2\",\"email\":\"user2@gmail.com\" }");
		  users.add("{ \"username\": \"user3\",\"password\": \"user3\",\"email\":\"user3@gmail.com\" }");
		  users.add("{ \"username\": \"user4\",\"password\": \"user4\",\"email\":\"user4@gmail.com\" }");
		  users.add("{ \"username\": \"user5\",\"password\": \"user5\",\"email\":\"user5@gmail.com\" }");
		  users.add("{ \"username\": \"user6\",\"password\": \"user6\",\"email\":\"user6@gmail.com\" }");
		  
		  //posting users and get ids
		  String url = "http://lccpu3.cse.ust.hk/gmission-dev/user/register" ;
		  try {
			  FileWriter writer = new FileWriter(usr_file);
			  for(int i = 0; i < users.size(); i++){
					JSONObject obj = new JSONObject(httpAgent.sendPost(url, users.get(i)));
					users_id.add(obj.getInt("id"));		
					writer.write(obj.getInt("id") + " ");
			  }
			  writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		
		  //posting locations and get ids
		  try {
			  FileWriter writer = new FileWriter(loca_file);
			  for(int i = 0; i < locations.size(); i++){
					JSONObject obj = httpAgent.postRestful("location",locations.get(i));
					locations_id.add(obj.getInt("id"));	
					writer.write(obj.getInt("id") + " ");
			  }		  
			  writer.close();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }

}
//return a ranomly picked user
public int getRandomUser(){
	Random random = new Random();
	int index = random.nextInt(users_id.size());
	return users_id.get(index);
	
}
public int getRandomLocation(){
	Random random = new Random();
	int index = random.nextInt(locations_id.size());
	return locations_id.get(index);
}
	                           
}
