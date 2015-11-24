package main;
import org.json.*;

public class Trial {
	
	
	
	public static void main(String[] args) throws Exception{  
		String param = "{ \"brief\":\"this is a simple answer posted by robin\", \"hit_id\":2, \"worker_id\":2,\"location_id\":1}";	
//		String param = "{\"type\":\"text\",\"brief\":\"this is a simple text question\",\"credit\":10,"		
//		+ "\"status\":\"open\",\"required_answer_count\":3,\"requester_id\":1,\"location_id\":1} ";
		HttpRequest httpAgent = new HttpRequest( );
		httpAgent.postRestful("answer", param);
//		httpAgent.getRestful("user", null);
//		httpAgent.getDefinitionList();
//		httpAgent.getDefinition("Location");
//		httpAgent.getUserDefinition();		
//		httpAgent.getHit();
//		httpAgent.getLocationList();
    } 
}
