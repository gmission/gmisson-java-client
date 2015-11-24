package main;

import java.io.BufferedReader;
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
import org.json.*;

public class HttpRequest {
    
	private String user_name = "free_test";
	private String password = "free_test";
	private String token = null;
	
	public HttpRequest(){
		String url = "http://lccpu3.cse.ust.hk/gmission-dev/user/auth" ;
		String param = "{ \"username\" : \"free_test\", \"password\":\"free_test\"}";
		String response = this.sendPost(url, param);
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
		System.out.println(response);
		return new JSONObject(response);
	}
	public JSONObject getHit(){
		String strURL = "http://lccpu3.cse.ust.hk/gmission-dev/rest/hit";
		String param = "q={\"filters\":[{\"name\":\"id\",\"op\":\"le\",\"val\":12}]}";
		String response = sendGet(strURL, param);
		System.out.println( response);
		return new JSONObject(response);
	}
	public JSONObject getRestful(String identity, String filter){
		String strURL = "http://lccpu3.cse.ust.hk/gmission-dev/rest/" + identity;
		String response = sendGet(strURL, filter);
		System.out.println( response);
		return new JSONObject(response);
	}
	public JSONObject postRestful(String identity, String param){
		String strURL = "http://lccpu3.cse.ust.hk/gmission-dev/rest/" + identity;
		System.out.println(strURL);
		System.out.println(param);
		String response = sendPost(strURL, param);
		System.out.println( response);
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
            String urlNameString = url ; //  
            if(param != null){
            	urlNameString = urlNameString +"?" + param;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection =(HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true); 
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
            connection.setRequestProperty("Authorization", "gMission " + token); 
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
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

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public String sendPost(String strURL, String params) {  
        try {  
            URL url = new URL(strURL);// 创建连接  
            HttpURLConnection connection = (HttpURLConnection) url  
                    .openConnection();  
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true);  
            connection.setRequestMethod("POST"); // 设置请求方式  
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
            //对象初始化login 的时候还没有token
            if(token != null){ 
            	connection.setRequestProperty("Authorization", "gMission " + token); 
            }
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
            connection.connect();  
            OutputStreamWriter out = new OutputStreamWriter(  
                    connection.getOutputStream(), "UTF-8"); // utf-8编码  
            out.append(params);  
            out.flush();  
            out.close();  
            // 读取响应  
            int length = (int) connection.getContentLength();// 获取长度  
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
                String result = new String(data, "UTF-8"); // utf-8编码  
                return result;  
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return "error"; // 自定义错误信息  
    }   
    
    
    static String string2Json(String s) { 
        StringBuilder sb = new StringBuilder(s.length()+20); 
        sb.append('\"'); 
        for (int i=0; i<s.length(); i++) { 
            char c = s.charAt(i); 
            switch (c) { 
            case '\"': 
                sb.append("\\\""); 
                break; 
            case '\\': 
                sb.append("\\\\"); 
                break; 
            case '/': 
                sb.append("\\/"); 
                break; 
            case '\b': 
                sb.append("\\b"); 
                break; 
            case '\f': 
                sb.append("\\f"); 
                break; 
            case '\n': 
                sb.append("\\n"); 
                break; 
            case '\r': 
                sb.append("\\r"); 
                break; 
            case '\t': 
                sb.append("\\t"); 
                break; 
            default: 
                sb.append(c); 
            } 
        } 
        sb.append('\"'); 
        return sb.toString(); 
     }
}

