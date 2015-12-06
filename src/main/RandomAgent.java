package main;

import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

/*
 If it is the first time for you to invoke this program, please set isPosred = false, and adapt the parameters for users and locations'
 parameters for your preference in the construction function.
 Otherwise, please set isPosred=true and manually set the start-to-end id in the private fields: uid_start uid_end lid_start lid_end
 */


public class RandomAgent {
	//list of registeres users(serve as requesters and workers)
	private ArrayList<Integer> users_id = new ArrayList<Integer>();
	//list of posted locations
	private ArrayList<Integer> locations_id = new ArrayList<Integer>();
	
	private HttpRequest httpAgent = null;
	
	
	private Boolean isPosted = true;
	
	//if already posted
	private int uid_start = 4;
	private int uid_end = 9;
	
	private int lid_start = 13;
	private int lid_end = 18;
	
public RandomAgent(HttpRequest agent){
	  HttpRequest httpAgent = agent;
	  if(isPosted){
		   //If already posted 
		   for(int i = uid_start; i <= uid_end ; i++){
				users_id.add(i);		
		  }
		  
		  for(int i = lid_start; i <= lid_end ; i++){
				locations_id.add(i);		
		  }
	  }
	  else{
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
		  users.add("{ \"username\": \"Jacby\",\"password\": \"Jacby\",\"email\":\"Jacby@gmail.com\" }");
		  users.add("{ \"username\": \"Tomy\",\"password\": \"Tomy\",\"email\":\"Tomym@gmail.com\" }");
		  users.add("{ \"username\": \"Edwardy\",\"password\": \"Edwardy\",\"email\":\"Edwardy@gmail.com\" }");
		  users.add("{ \"username\": \"Benny\",\"password\": \"Benny\",\"email\":\"Benny@gmail.com\" }");
		  users.add("{ \"username\": \"Joey\",\"password\": \"Joey\",\"email\":\"Joey@gmail.com\" }");
		  users.add("{ \"username\": \"Lucas\",\"password\": \"Lucas\",\"email\":\"Lucas@gmail.com\" }");
		  
		  //posting users and get ids
		  String url = "http://lccpu3.cse.ust.hk/gmission-dev/user/register" ;
		  for(int i = uid_start; i < users.size(); i++){
				JSONObject obj = new JSONObject(httpAgent.sendPost(url, users.get(i)));
				users_id.add(obj.getInt("id"));		
		  }
		  
		  System.out.println("users' id start from " + users_id.get(0) + " to " +  users_id.get(users_id.size()-1));
		  //posting locations and get ids
		  for(int i = 0; i < locations.size(); i++){
				JSONObject obj = httpAgent.postRestful("location",locations.get(i));
				locations_id.add(obj.getInt("id"));		
		  }
		  System.out.println("locations' id start from " + locations_id.get(0) + " to " +  locations_id.get(users_id.size()-1));
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
