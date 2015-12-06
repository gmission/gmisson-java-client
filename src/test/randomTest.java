package test;
import java.util.ArrayList;

import org.json.JSONObject;

import main.HttpRequest;
import main.RandomAgent;

public class randomTest {

	public static void main(String[] args) {
		System.out.println("{ \"username\": \"Jack\",\"password\": \"Jack\",\"email\":\"Jack@gmail.com\" }");
		HttpRequest httpAgent = new HttpRequest();
//		ArrayList<String> locations = new ArrayList<String>();
		String user1 = "{" +"\"username\":" +"\"" +"Jack0" +"\""   +",\"password\":" +"\"" +"Jack0" +"\""  +",\"email\":" +"\"" +"Jack0@gmail.com" +"\"" +"}";
		System.out.println(user1);
		String url = "http://lccpu3.cse.ust.hk/gmission-dev/user/register" ;
		JSONObject obj = new JSONObject(httpAgent.sendPost(url, user1));
		System.out.println(obj);
	}
}
