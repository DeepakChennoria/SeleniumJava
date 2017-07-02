package com.SeleniumJava.utils;

import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
 
 
public class JsonReader {
 
    
    public static LinkedList<String> getData(String key) {
        JSONParser parser = new JSONParser();
        List<String> values = null;
        LinkedList<String> collection = new LinkedList<String>();
 
        try {
 
        	Object obj = parser.parse(new FileReader(System.getProperty("user.dir")
					+ "\\src\\test\\java\\testData\\testData.json"));
 
            JSONObject jsonObject = (JSONObject) obj;
 
            JSONArray companyList = (JSONArray) jsonObject.get(key);
            
            Iterator<String> iterator = companyList.iterator();
            while (iterator.hasNext()) {
            	collection.add(iterator.next());
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		return collection;
    }
    
}
