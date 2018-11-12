package com.hzu.homework.util;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	public static String parseJSON(Map<String, Object> map) throws IOException{
		/*Map<String, Object> returnMap = new HashMap<String, Object>();
		Iterator<String> iterator = map.keySet().iterator();
		HashMap<String, Object> data = new HashMap<String, Object>();
		while(iterator.hasNext()){
			String key = iterator.next();
			if(!key.equals("return_code")&&!key.equals("message")){
				data.put(key, map.get(key));
			}else {
				returnMap.put(key, map.get(key));
			}
		}
		returnMap.put("data", data);
		
		return JSON.toJSONString(returnMap,SerializerFeature.WriteNullStringAsEmpty);*/
//		return JSON.toJSONString(map,SerializerFeature.WriteNullStringAsEmpty);
		String returnStr = "";
        try {
			returnStr =  mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
        return returnStr;
	}

}
