package com.zxl.service.sypro;

import java.util.ArrayList;
import java.util.List;


public class Test {
	public static void main(String[] args) {
		StringBuilder resourcesTextList = new StringBuilder();
		List<String> menus = new ArrayList<String>();
		menus.add("aa");
		for (String syMenu : menus) {
			resourcesTextList.append(syMenu).append(",");
		}
		System.out.println(resourcesTextList.length());
		System.out.println(resourcesTextList.substring(1));
	}
}
