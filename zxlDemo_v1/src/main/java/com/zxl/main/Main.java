package com.zxl.main;

import com.jfinal.core.JFinal;

public class Main {

	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 80, "/", 5);
	}

}
