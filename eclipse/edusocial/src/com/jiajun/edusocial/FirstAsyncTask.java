package com.jiajun.edusocial;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class FirstAsyncTask extends AsyncTask<Void, Void, Void>{
	private HttpResponse httpResponse = null;
	private HttpEntity httpEntity = null;

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub

		HttpGet httpGet = new HttpGet("http://222.17.107.130:8080/test.json");

		HttpClient httpClient = new DefaultHttpClient();

		InputStream inputStream = null;
		try {
			httpResponse = httpClient.execute(httpGet);
			httpEntity = httpResponse.getEntity();
			inputStream = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"GB2312"));
			String result = "";
			String line = "";
			while((line = reader.readLine()) != null){
				result = result + line;
			}
			System.out.println(result);
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();      
	        Map<String, String> map = null; 
	        
			JSONObject jsonObject=new JSONObject(result);     //返回的数据形式是一个Object类型，所以可以直接转换成一个Object      
            String name=jsonObject.getString("name");             
            int age=jsonObject.getInt("age");      
            Log.i("abc", "name:" + name + " | age:" + age);
            
            JSONObject contentObject=jsonObject.getJSONObject("content");       //获取对象中的对象      
            String questionsTotal=contentObject.getString("questionsTotal");    //获取对象中的一个值      
            Log.i("abc", "questionsTotal:" + questionsTotal);   
            
            JSONArray contentArray=contentObject.getJSONArray("questions");     //获取对象中的数组      
            for (int i = 0; i < contentArray.length(); i++) {      
                JSONObject item = contentArray.getJSONObject(i); // 得到每个对象      
                String question = item.getString("question");   // 获取对象对应的值      
                String answer = item.getString("answer");      
     
                map = new HashMap<String, String>(); // 存放到MAP里面      
                map.put("question", question);      
                map.put("answer", answer);      
                list.add(map);      
            }      
            for (Map<String, String> list2 : list) {      
                String question = list2.get("question");      
                String answer = list2.get("answer");      
                Log.i("abc", "question:" + question + " | answer:" + answer);      
            }


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
				inputStream.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}

}
