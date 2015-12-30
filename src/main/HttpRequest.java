package main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageInputStream;

import org.json.*;

public class HttpRequest {
    
	private String user_name = "u9";
	private String password = "u9";
	private String email = "u9@gmail.com";
	private String token = null;
	
	public HttpRequest(){
		//authorization
		String url = "http://lccpu3.cse.ust.hk/gmission-dev/user/auth" ;
		String param = "{\"username\":\""+ user_name + "\",\"password\":\""+ password +"\"}";
		String response = this.sendPost(url, param);
		
		//in case the user has not yet been registered
		if(response.equals("error")){
			String url_register = "http://lccpu3.cse.ust.hk/gmission-dev/user/register" ;
			String param_register = "{\"username\":\""+ user_name + "\",\"password\":\""+ password + "\",\"email\":\"" + email + "\"}";
			
			//register the user
			String resp = this.sendPost(url_register, param_register);
			if(resp.equals("error")){
				System.err.println("error happens in regsitering an user during constructing a HttpRequest object");
				System.exit(1);
			}
			//re-authorization
			response = this.sendPost(url, param);
		}
		JSONObject rtn_json = new JSONObject(response);
		token = rtn_json.getString("token");
	}
	
	
	public JSONObject getDefinitionList(){
		String strURL = " http://lccpu3.cse.ust.hk/gmission-dev/definitions/";
		String response = sendGet(strURL, null);
		System.out.println(response);
		return new JSONObject(response);
	}
	
	public JSONObject getDefinition(String param){
		String strURL = " http://lccpu3.cse.ust.hk/gmission-dev/definitions/" + param;
		String response = sendGet(strURL, null);
		System.out.println(response);
		return new JSONObject(response);
	}
	public JSONObject getUserDefinition(){
		String strURL = " http://lccpu3.cse.ust.hk/gmission-dev/definitions/User";
		String response = sendGet(strURL, null);
		return new JSONObject(response);
	}
//	public JSONObject getHit(){
//		String strURL = "http://lccpu3.cse.ust.hk/gmission-dev/rest/hit";
//		String param = "q={\"filters\":[{\"name\":\"id\",\"op\":\"le\",\"val\":12}]}";
//		String response = sendGet(strURL, param);
//		return new JSONObject(response);
//	}
	public JSONObject getRestful(String identity, String filter){
		String strURL = "http://lccpu3.cse.ust.hk/gmission-dev/rest/" + identity;
		String response = sendGet(strURL, filter);
		System.out.println(response);
		return new JSONObject(response);
	}
	public JSONObject postRestful(String identity, String param){
		String strURL = "http://lccpu3.cse.ust.hk/gmission-dev/rest/" + identity;
		String response = sendPost(strURL, param);
		return new JSONObject(response);
	}
	
    public  String sendGet(String url, String param) {
    	if(token == null){
    		System.err.println("Token is not initialized. ");
    		System.exit(1);
    	}
    	String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url ; 
            if(param != null){
            	urlNameString = urlNameString +"?" + param;
            }
            URL realUrl = new URL(urlNameString);
            //build connection
            HttpURLConnection connection =(HttpURLConnection)realUrl.openConnection();
            // set properties
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true); 
            connection.setRequestProperty("Accept", "application/json"); 
            connection.setRequestProperty("Authorization", "gMission " + token); 
            // get connected
            connection.connect();
            // get response
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("Exception happens when sending Get request！" + e);
            e.printStackTrace();
        }
        // close input stream
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public String sendPost(String strURL, String params) {  
        try {  
        	//build connection
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url  
                    .openConnection();  
            //set properties of connection
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true);  
            connection.setRequestMethod("POST");  
            connection.setRequestProperty("Accept", "application/json"); 
            //no token is needed during authorization when constructing the HttpRequest object
            if(token != null){ 
            	connection.setRequestProperty("Authorization", "gMission " + token); 
            }
            // set properties
            connection.setRequestProperty("Content-Type", "application/json"); 
            //build connection
            connection.connect();  
            OutputStreamWriter out = new OutputStreamWriter(  
                    connection.getOutputStream(), "UTF-8"); 
            out.append(params);  
            out.flush();  
            out.close();  
            // get response
            int length = (int) connection.getContentLength();
            InputStream is = connection.getInputStream();  
            if (length != -1) {  
                byte[] data = new byte[length];  
                byte[] temp = new byte[512];  
                int readLen = 0;  
                int destPos = 0;  
                while ((readLen = is.read(temp)) > 0) {  
                    System.arraycopy(temp, 0, data, destPos, readLen);  
                    destPos += readLen;  
                }  
                String result = new String(data, "UTF-8"); 
                return result;  
            }  
        } catch (IOException e) {  
        	//token == null means indicates the user has not been registered yet, so register that user during construction would solve the problem
        	if(token != null){
	            e.printStackTrace();  
        	}
        }  
        return "error"; 
    }   
    
    
    public String postFile( File file){
    	String strURL = "http://lccpu3.cse.ust.hk/gmission-dev/image/upload";
    	//obtain the byte array of the uploaded picture
    	byte[] pixels = image2byte(file);
    	
    	//Static stuff
    	String attachmentName = "file" ;
    	String attachmentFileName = new String(file.getName());
    	String crlf = "\r\n";
    	String twoHyphens = "--";
    	String boundary =  "*****";
    	String response;
    	//send the request
    	try {  
    		//Setup the request
    		HttpURLConnection httpUrlConnection = null;
        	URL url = new URL(strURL);
        	httpUrlConnection = (HttpURLConnection) url.openConnection();
        	httpUrlConnection.setUseCaches(false);
        	httpUrlConnection.setDoOutput(true);       	 
        	httpUrlConnection.setRequestMethod("POST");
        	//set connection properties
        	httpUrlConnection.setRequestProperty("Accept", "application/json"); 
        	if(token != null){ 
       		 httpUrlConnection.setRequestProperty("Authorization", "gMission " + token); 
            }
        	httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
        	httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
        	httpUrlConnection.setRequestProperty(
        	    "Content-Type", "multipart/form-data;boundary=" + boundary);
        	//Start content wrapper:
        	DataOutputStream request = new DataOutputStream(
        		    httpUrlConnection.getOutputStream());

        		request.writeBytes(twoHyphens + boundary + crlf);
        		request.writeBytes("Content-Disposition: form-data; name=\"" +
        		    attachmentName + "\";filename=\"" + 
        		    attachmentFileName + "\"" + crlf);
        		request.writeBytes(crlf);
        		//End content wrapper:
        		request.write(pixels);
        		request.writeBytes(crlf);
        		request.writeBytes(twoHyphens + boundary + 
        		    twoHyphens + crlf);
        		//Flush output buffer:
        		request.flush();
        		request.close();
        		//Get response:
        		InputStream responseStream = new 
        			    BufferedInputStream(httpUrlConnection.getInputStream());

        			BufferedReader responseStreamReader = 
        			    new BufferedReader(new InputStreamReader(responseStream));

        			String line = "";
        			StringBuilder stringBuilder = new StringBuilder();

        			while ((line = responseStreamReader.readLine()) != null) {
        			    stringBuilder.append(line).append("\n");
        			}
        			responseStreamReader.close();

        			 response = stringBuilder.toString();
        			//Close response stream
        			responseStream.close();
        			//Close the connection:
        			httpUrlConnection.disconnect();
    	}catch (IOException e) {  
            e.printStackTrace();  
            return "error";
        }  

    	return response; 
    }
    
    //transform an image into a byte array
    public byte[] image2byte(File file){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
 
          input = new FileImageInputStream(file);
          ByteArrayOutputStream output = new ByteArrayOutputStream();
          byte[] buf = new byte[1024];
          int numBytesRead = 0;
          while ((numBytesRead = input.read(buf)) != -1) {
          output.write(buf, 0, numBytesRead);
          }
          data = output.toByteArray();
          output.close();
          input.close();
        }
        catch (FileNotFoundException ex1) {
          ex1.printStackTrace();
        }
        catch (IOException ex1) {
          ex1.printStackTrace();
        }
        return data;
      }
}

