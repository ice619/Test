package com.chenpeng;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
	public static void main(String[] args) {
//		String input = "abc";
//        String regex = "({1})";
//        input = input.replaceAll (regex, "%");
//        System.out.println (input);
		
//		System.out.println(System.currentTimeMillis());
		
//		System.out.println( (int)((Math.random()*9+1)*100000) + "");
//		Random random = new Random();
//		int nextInt = random.nextInt(1000); //100是不包含在内的，只产生0~99之间的数。
//		System.out.println(nextInt);
		
		/*String a = "PK_CUPC_20170302.xls";
		System.out.println(a.substring(a.length() - 12, a.length() - 4));
		String b = "T_4801273956_20170228.xlsx";
		System.out.println(b.substring(b.length() - 13, b.length() - 5));*/
		/*int count = 0;
		int row = 2;
		int pagecount = 0;
		int a= count % row;
		pagecount = count % row == 0 ? count / row : count / row + 1;
		System.out.println(a +";"+pagecount);*/
		
		List<String> list = new ArrayList<String>();
		
		for(int i = 0; i < 5; i ++){
			list.add("1");
		}
		System.out.println(list.toString());
	}
}
