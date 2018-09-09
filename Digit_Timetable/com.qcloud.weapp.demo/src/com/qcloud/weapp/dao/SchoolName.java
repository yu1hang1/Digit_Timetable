package com.qcloud.weapp.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import com.ican.yueban.jwgl.sut.IOUtils;



public class SchoolName {
	
	
	

	public List<Object[]> schoolname()
			throws ClientProtocolException, IOException {
		
		 String [][] array = new String[6][2];
		 
		 array[0][0]="qust";
		 array[0][1]="青岛科技大学";
		 array[1][0]="sut";
		 array[1][1]="沈阳工业大学(中央校区)";
		 array[2][0]="tjpu";
		 array[2][1]="天津工业大学";
		 array[3][0]="sdjzu";
		 array[3][1]="山东建筑大学";
		 array[4][0]="hblg";
		 array[4][1]="华北理工大学";
		 array[5][0]="lnnu";
		 array[5][1]="辽宁师范大学";
	    List<Object[]>  list = new ArrayList<Object[]> ();//定义一个list对象
	    
	   for(int i=0;i<array.length;i++)
	   {
		   Object[] ob = new Object[2];//定义一个数组对象
		   ob[0]=array[i][0];
		   ob[1]=array[i][1];
		   list.add(ob);
	   }
	   
	  

	   
		return list;
	}
	
	public static void main(String[] args) {
		
			System.out.println("查询个人课表测试-------");
			
	}

}
