package com.tianer.controller.storepc.store_redpackets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.fabric.xmlrpc.base.Array;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

public class Main {
	
	public static void main(String[] args) {
		
		/*int num = (int)((Math.random()*9+1)*100000);
		//DateFormat format = new SimpleDateFormat("yyyyMMdd");
		DateUtil date = new DateUtil();
		String da = date.getDays();
		
		String id = da+""+num;
		
		System.out.println(da);
		System.out.println(num);
		System.out.println(id);*/
		
		
		String name = "1,2,3,4";
		/*String str =name.replace("[", "");
		String a = str.replace("]", "");
		String[] s =a.split(",");
		System.out.println(s);*/
		String[] s =name.split(",");
		for (int i = 0; i < s.length; i++) {
			//Map<Object , Object> map = new HashMap<Object, Object>();
			List<Object> list = new ArrayList<Object>();
			//map.put(i, s[i]);
			list.add(s[i]);
			System.out.println(list);
		}
	}

}
