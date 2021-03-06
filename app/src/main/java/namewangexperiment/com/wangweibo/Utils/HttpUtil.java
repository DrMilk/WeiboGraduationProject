package namewangexperiment.com.wangweibo.Utils;

/**
 * Created by Grampus on 2017/3/17.
 */



import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * HttpClient GET POST PUT 请求
 * @author huang
 * @date 2013-4-10
 */
public class HttpUtil
{





    public static String httpGet(String getUrl,Map<String, String> getHeaders) throws IOException {
        URL getURL = new URL(getUrl);
        HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();

        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");//在get请求中这是能在各个浏览器兼容json
        if(getHeaders != null) {
            for(String pKey : getHeaders.keySet()) {
                connection.setRequestProperty(pKey, getHeaders.get(pKey));
            }
        }
        connection.connect();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sbStr = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sbStr.append(line);
        }
        bufferedReader.close();
        connection.disconnect();
        return new String(sbStr.toString().getBytes(),"utf-8");
    }
    public static String httpPost(String postUrl,Map<String, String> postHeaders, String postEntity) throws IOException {
        URL postURL = new URL(postUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) postURL.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setInstanceFollowRedirects(true);
        //application/json x-www-form-urlencoded
        //httpURLConnection.setRequestProperty("Content-Type",  "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Type",  "application/json");
        StringBuilder sbStr = new StringBuilder();
        if(postHeaders != null) {
            for(String pKey : postHeaders.keySet()) {
                httpURLConnection.setRequestProperty(pKey, postHeaders.get(pKey));
            }
        }
        if(postEntity != null) {
            JSONObject obj= null;
            try {
                obj = new JSONObject(postEntity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PrintWriter out = new PrintWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8"));
            out.println(obj);
            out.close();
            //httpURLConnection.getInputStream()
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));  //解决返回值汉字乱码的问题

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sbStr.append(inputLine);
            }
            in.close();
        }
        httpURLConnection.disconnect();
//        System.out.println("fsdfdf:"+sbStr.toString());
        return new String(sbStr.toString().getBytes(),"utf-8");
    }
    public static String HttpPut(String postUrl,Map<String, String> postHeaders,String postEntity) throws Exception {
        URL postURL = new URL(postUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) postURL.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("PUT");
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setInstanceFollowRedirects(true);
        //application/json x-www-form-urlencoded
        //httpURLConnection.setRequestProperty("Content-Type",  "application/x-www-form-urlencoded");//表单上传的模式
        httpURLConnection.setRequestProperty("Content-Type",  "application/json;charset=utf-8");//json格式上传的模式
        StringBuilder sbStr = new StringBuilder();
        if(postHeaders != null) {
            for(String pKey : postHeaders.keySet()) {
                httpURLConnection.setRequestProperty(pKey, postHeaders.get(pKey));
            }
        }
        if(postEntity != null) {
            JSONObject obj=new JSONObject(postEntity);

            PrintWriter out = new PrintWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8"));
            out.println(obj);
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection
                    .getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sbStr.append(inputLine);
            }
            in.close();
        }
        httpURLConnection.disconnect();
        return new String(sbStr.toString().getBytes(),"utf-8");
    }
}
