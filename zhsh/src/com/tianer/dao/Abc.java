package com.tianer.dao;

public class Abc {
	
	public static void main(String[] args) {
		int a = checkFile("abc.a.a.a.jpg");
		System.out.println(a);
	}
	
	public static int checkFile(String filename){
		String extName = filename.substring(filename.lastIndexOf("."));
		if(extName.equals(".jpg") || extName.equals(".png")){
			return 0;
		}else{
			return 1;
		}
		
	}

}
