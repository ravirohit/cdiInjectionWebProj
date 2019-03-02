package com.learn.mainTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class mainApp {

	public static void main(String[] args) {
		HashMap<String,String> map = new HashMap<>();
		map.put("key1", "rohit");
		map.put("key2", "ravi");
		map.put("key3", "raj");
		map.put("key4", "ranjan");
		Set<Entry<String,String>> set = map.entrySet();
		
		System.out.println(set);
        System.out.println(map);
        System.out.println(map.size());
	}

}
