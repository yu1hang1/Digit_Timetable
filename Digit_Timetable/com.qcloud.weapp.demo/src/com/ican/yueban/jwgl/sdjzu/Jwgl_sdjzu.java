 package com.ican.yueban.jwgl.sdjzu;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import sun.misc.BASE64Encoder;

public class Jwgl_sdjzu {
	private static String stuNumber = "";
	private static String stuName = "";
	private static String Cookie = "";
	private String indexUrl = GlobalConstant.INDEX_URL;
	private String secretCodeUrl = GlobalConstant.SECRETCODE_URL;
	private String loginUrl = GlobalConstant.LOGIN_URL;
	private String mainUrl = GlobalConstant.MAIN_URL;
	private String queryClassroomUrl = GlobalConstant.QUERY_CLASSROOM_URL;
	private String queryClassroomGnmkdm = GlobalConstant.QUERY_CLASSROOM_GNMKDM;
	private String queryStuGradeUrl = GlobalConstant.QUERY_STU_GRADE_URL;
	private String queryStuGradeGnmkd = GlobalConstant.QUERY_STU_GRADE_GNMKDM;
	private String queryStuCourseUrl = GlobalConstant.QUERY_STU_COURSE_URL;
	private String queryStuCourseGnmkd = GlobalConstant.QUERY_STU_COURSE_GNMKDM;
	private String xixiaoqu = GlobalConstant.XIXIAOQU;
	private String identityStu = GlobalConstant.IDENTITY_STU;
	CloseableHttpClient client ;
	/**
	 * 登录功能
	 * 
	 * @param stuNumber
	 * @param password
	 * @return
	 * @throws Exception
	 * @throws UnsupportedOperationException
	 */
	
	public static String getImageStr(String imgFile) {
	    InputStream inputStream = null;
	    byte[] data = null;
	    try {
	        inputStream = new FileInputStream(imgFile);
	        data = new byte[inputStream.available()];
	        inputStream.read(data);
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 加密
	    BASE64Encoder encoder = new BASE64Encoder();
	   // System.out.print(encoder.encode(data));
	    
	    return Base64.encodeBase64String(data);
	    //return encoder.encode(data);
	    
	}
	
	
	public String yanzhengma()throws UnsupportedOperationException, Exception{
		HttpGet secretCodeGet = new HttpGet(secretCodeUrl);
		client =  HttpClients.createDefault();
		CloseableHttpResponse responseSecret = client.execute(secretCodeGet);
		// 获取返回的Cookie
		//Cookie = responseSecret.getFirstHeader("Set-Cookie").getValue();
		System.out.println(Cookie);
		String viewState = IOUtils.getViewState(indexUrl, "", "");
		// 将验证码下载到C盘
		Calendar now = Calendar.getInstance();
		int second = now.get(Calendar.SECOND);
		int mm = now.get(Calendar.MILLISECOND);
		String name = ""+second+mm;
		// 将验证码下载到C盘
	//	IOUtils2.getSecret(responseSecret.getEntity().getContent(),
			//	"name.png", "/home/t");
		//System.out.println("jasja"+IOUtils2.convertStreamToString(responseSecret.getEntity().getContent()));
		 // String strImg = getImageStr("/home/t/tempname.png");
		
		/*linux版本
		  IOUtils.getSecret(responseSecret.getEntity().getContent(),
					name+".png", "/tmp/temp/t");
			  String strImg = getImageStr("/tmp/temp/"+"t"+name+".png");
			  linux版本
			  */
		IOUtils.getSecret(responseSecret.getEntity().getContent(),
				name+".png", "d://");
		  String strImg = getImageStr("d:/"+name+".png");
	
		   System.out.println(strImg);
		   return "";
		    
	}
	
	public String login(String stuNumber, String password,String secret)
			throws UnsupportedOperationException, Exception {
		
		client =  HttpClients.createDefault();
		this.stuNumber = stuNumber;
		// 获取验证码
		
		System.out.println(secret);
		HttpPost loginPost = new HttpPost(loginUrl);// 创建登录的Post请求
		//loginPost.setHeader("Cookie", Cookie);// 带上第一次请求的Cookie
		List<NameValuePair> nameValuePairLogin = new ArrayList<NameValuePair>();// 封装Post提交参数
		nameValuePairLogin
				.add(new BasicNameValuePair("userName", stuNumber));// 隐藏表单值
		nameValuePairLogin
				.add(new BasicNameValuePair("password", password));// 学号
		//nameValuePairLogin.add(new BasicNameValuePair("Agnomen", secret));// 密码
		nameValuePairLogin.add(new BasicNameValuePair("image.x", "26"));// 验证码
		nameValuePairLogin.add(new BasicNameValuePair("image.y","10"));// 身份,默认学生
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
				nameValuePairLogin, "UTF-8");
		loginPost.setEntity(entity);
		HttpResponse responseLogin = client.execute(loginPost);
		// client1.close();
		// 第三步:判断提交数据是否成功，成功返回302
	
		System.out.println("jjjj"+responseLogin.getStatusLine().getStatusCode());
		
		if (responseLogin.getStatusLine().getStatusCode() == 302) {
			
			
			
			
		
			/*
			String newQueryStuCourseUrl = "http://jwc.sut.edu.cn/ACTIONQUERYELECTIVERESULTBYSTUDENT.APPPROCESS?mode=1";
			System.out.println(Cookie);
			System.out.println(newQueryStuCourseUrl);
			
			HttpGet mainGet = new HttpGet(newQueryStuCourseUrl);
			//mainGet.setHeader("Cookie", Cookie);
			mainGet.setHeader("Referer", "http://jwc.sut.edu.cn/ACTIONQUERYSTUDENTSCHEDULE.APPPROCESS");
			HttpResponse responseMain = client.execute(mainGet);
			InputStream is = responseMain.getEntity().getContent();
			String html = "";
			html = IOUtils.getHtml(is, "GBK");
			System.out.println(html);*/
			
			
			// 如果提交成功，带着Cookie请求重定向的main页面，并获取学生姓名
			HttpGet mainGet = new HttpGet(mainUrl);
			//mainGet.setHeader("Cookie", Cookie);
			mainGet.setHeader("Referer", loginUrl);
			HttpResponse responseMain = client.execute(mainGet);
			InputStream is = responseMain.getEntity().getContent();
			String html = "";
			
		
			try {
						
				html = IOUtils.getHtml(is, "UTF-8");
				
		//		Document docCourse = Jsoup.parse(html);
			//	Elements eleCourse = docCourse.select("td");
			//	for (int i = 2; i < eleCourse.size(); i++) {
				//	System.out.print(eleCourse.get(2).text() + "	");
				//	if (i % 9 == 0) {
					//	System.out.println();
				//	}
				//}
				//System.out.println(html);
			} catch (Exception e) {
				System.out.println("解析html失败！");
				e.printStackTrace();
			}
			
			Document docCourse = Jsoup.parse(html);
			Elements eleCourse = docCourse.select("td");
		//	for (int i = 2; i < eleCourse.size(); i++) {
				//System.out.print(eleCourse.get(2).text() + "	");
			//stuName = Jsoup.parse(html).getElementById("xhxm").text();
			String s =eleCourse.get(1).text();
	        System.out.println("登录成功！欢迎您：" +s);
	        int left = s.indexOf("息")+2;
	    	System.out.println(left);
	        int right = s.indexOf('，');
	    	System.out.println(right);
	        String n=s.substring(left+1,right);
	         
         
		System.out.println("登录成功！欢迎您：" +n);
			//System.out.println("登录成功！欢迎您：" +eleCourse.get(1).text());
			//client.close();
			return "欢迎您："+n;
		} else {
			System.out.println("登录失败！");
			//client.close();
			return "no";
		}

	}

	

	/**
	 * 查询个人课表方法
	 * 
	 * @param xnd
	 * @param xqd
	 * @return 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<Object[]> queryStuCourse(String xnd, String xqd)
			throws ClientProtocolException, IOException {
		String newQueryStuCourseUrl = "http://jwfw1.sdjzu.edu.cn/ssfw/jwnavmenu.do?menuItemWid=1D2D0E9EC0D913CEE0540010E0235320";
		System.out.println(Cookie);
		System.out.println(newQueryStuCourseUrl);
		
		HttpGet mainGet = new HttpGet(newQueryStuCourseUrl);
		//mainGet.setHeader("Cookie", Cookie);
		mainGet.setHeader("Referer", "http://jwfw1.sdjzu.edu.cn/ssfw/index.do");
		HttpResponse responseMain = client.execute(mainGet);
		InputStream is = responseMain.getEntity().getContent();
		String html = "";
		html = IOUtils.getHtml(is, "UTF-8");
		//System.out.println(html);
		  Document docCourse = Jsoup.parse(html);
		//  System.out.println(docCourse );
		  String [][] array = new String[12][16];
		  for(int i=0;i<12;i++)
		  {
			  for(int j=0;j<16;j++)
			  {
				  array[i][j]="";
			  }
		  }
			Elements eleCourse = docCourse.select("td");
			System.out.println(eleCourse);
			ArrayList<String> listSub=new ArrayList<String>();
			int arrayi=0;
			int arrayj=0;
			for (int i = 1; i < eleCourse.size(); i++) {
				String temp = null;
				  Pattern p1 = Pattern.compile("rowspan=\"(.+?)\"");
				    Matcher m1 = p1.matcher(i+eleCourse.get(i).toString());

				    while(m1.find()) {
				   
				        temp = m1.group(0).replaceAll("rowspan=\"", "").replaceAll("\"", "");
				        System.out.println(i+temp);							      
				    }
				    if(temp!=null){
				    	
				    if(arrayi>=12)
				    	break;
				    if(array[arrayi][arrayj].equals("0"))
				    {
				    	for(int x=0;x<16;x++)
				    	{
				    	arrayj++;
				    	arrayj++;
				    	  if(arrayj>=16)
						    {
				    		  arrayj=0;
						    	arrayi++;
						    	break;
						    }
				    	if(!array[arrayi][arrayj].equals("0"))
				    	{
				    		break;
				    	}
				    	}
				    }
				    array[arrayi][arrayj]=temp;
				    for(int x=1;x<Integer.parseInt(temp);x++)
				    {
				    	int temp1=arrayi+x;
				    	array[temp1][arrayj]="0";
				    }
				    arrayj++;
				    if(eleCourse.get(i).toString().indexOf("<br><br>")!=-1){
				    	array[arrayi][arrayj]=eleCourse.get(i).text()+" no";
				    }
				    else
				    {
				    	array[arrayi][arrayj]=eleCourse.get(i).text();
				    }
				    arrayj++;
				    if(arrayj>=16)
				    {
				    	arrayj=0;
				    	arrayi++;
				    }
				    }
				System.out.println(i+eleCourse.get(i).text() + "	");
				System.out.println(i+eleCourse.get(i).toString()+ "	");
			}
			 for(int i=0;i<12;i++)
			  {
				  for(int j=0;j<16;j++)
				  {
					 System.out.print(array[i][j]+","); 
				  }
				  System.out.println();
			  }
	    
	    List<Object[]>  list = new ArrayList<Object[]> ();//定义一个list对象
	   
	  
	    for(int j=2;j<16;)
	    {
	    	for(int i=0;i<12;i++)
	    	{
	    		if(array[i][j]!="0")
	    		{
	    			 Object[] ob = new Object[10];//定义一个数组对象
	    			int j1=j+1;
	    			if(array[i][j1].length()>=2){
	    				String [] arr = array[i][j+1].split("\\s+");
	    				for(String ss : arr){
	    				    System.out.println(ss);
	    				}
	    				ob[0]=j1/2;
	    				ob[1]="";
	    				ob[2]=arr[0];
	    				if(arr[4].equals("no")){
	    					ob[3]="";
		    				ob[6]=arr[3];
	    				}
	    				else
	    				{
	    				ob[3]=arr[3];
	    				ob[6]=arr[4];
	    				}
	    				String str = arr[1];
	 			    	String reg = "[\u4e00-\u9fa5]";
	 			    	Pattern pat = Pattern.compile(reg);  
	 			    	Matcher mat=pat.matcher(str); 
	 			    	String repickStr = mat.replaceAll("");
	 			    	System.out.println("去中文后:"+repickStr);	 		
	 				 ob[4]=repickStr.replaceAll(",", ".");
	 				ob[5]="整周";
	 				
	 				int top=3*i;
	 				ob[7]=top+"rem";
	 				int height=3*Integer.parseInt(array[i][j]);
	 				ob[8]=height+"rem";
	 				ob[9]=arr[2];
	 				list.add(ob);
	    				System.out.print("sss"+array[i][j+1]);
	    			}
	    		}
	    	}
	    	System.out.println();
	    	j++;
	    	j++;
	    }
	

	    int[] temp={0,0,0,0,0,0,0};
	    for(int i = 0;i < list.size(); i ++){
	    	  Object[] ob1 = list.get(i);//获取对象
	    	  
	    	  
	    	    if(ob1[0].equals("TABLE1#1"))
	    	    {
	    	    	ob1[0]="1";
	    	    	ob1[7] = "1.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周一  1-2节";
	    		    
	    	    }
	    	    if(ob1[0].equals("TABLE1#2"))
	    	    {
	    	    	ob1[0]="2";
	    	    	ob1[7] = "1.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周二  1-2节";
	    	    }
	    	    if(ob1[0].equals("TABLE1#3"))
	    	    {
	    	    	ob1[0]="3";
	    	    	ob1[7] = "1.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周三  1-2节";
	    	    }
	    	    if(ob1[0].equals("TABLE1#4"))
	    	    {
	    	    	ob1[0]="4";
	    	    	ob1[7] = "1.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周四  1-2节";
	    	    }
	    	    if(ob1[0].equals("TABLE1#5"))
	    	    {
	    	    	ob1[0]="5";
	    	    	ob1[7] = "1.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周五 1-2节";
	    	    }
	    	    if(ob1[0].equals("TABLE1#6"))
	    	    {
	    	    	ob1[0]="6";
	    	    	ob1[7] = "1.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周六 1-2节";
	    	    }
	    	    if(ob1[0].equals("TABLE1#7"))
	    	    {
	    	    	ob1[0]="7";
	    	    	ob1[7] = "1.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周日 1-2节";
	    	    }
	    	    if(ob1[0].equals("TABLE2#1"))
	    	    {
	    	    	ob1[0]="1";
	    	    	ob1[7] = "7.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周一 3-4节";
	    	    }
	    	    if(ob1[0].equals("TABLE2#2"))
	    	    {
	    	    	ob1[0]="2";
	    	    	ob1[7] = "7.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周二 3-4节";
	    	    }
	    	    if(ob1[0].equals("TABLE2#3"))
	    	    {
	    	    	ob1[0]="3";
	    	    	ob1[7] = "7.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周三3-4节";
	    	    }
	    	    if(ob1[0].equals("TABLE2#4"))
	    	    {
	    	    	ob1[0]="4";
	    	    	ob1[7] = "7.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周四3-4节";
	    	    }
	    	    if(ob1[0].equals("TABLE2#5"))
	    	    {
	    	    	ob1[0]="5";
	    	    	ob1[7] = "7.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周五 3-4节";
	    	    }
	    	    if(ob1[0].equals("TABLE2#6"))
	    	    {
	    	    	ob1[0]="6";
	    	    	ob1[7] = "7.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周六 3-4节";
	    	    }
	    	    if(ob1[0].equals("TABLE2#7"))
	    	    {
	    	    	ob1[0]="7";
	    	    	ob1[7] = "7.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周一日3-4节";
	    	    }
	    	    if(ob1[0].equals("TABLE3#1"))
	    	    {
	    	    	ob1[0]="1";
	    	    	ob1[7] = "13.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周一 5-6节";
	    	    }
	    	    if(ob1[0].equals("TABLE3#2"))
	    	    {
	    	    	ob1[0]="2";
	    	    	ob1[7] = "13.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周二 5-6节";
	    	    }
	    	    if(ob1[0].equals("TABLE3#3"))
	    	    {
	    	    	ob1[0]="3";
	    	    	ob1[7] = "13.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周三 5-6节";
	    	    }
	    	    if(ob1[0].equals("TABLE3#4"))
	    	    {
	    	    	ob1[0]="4";
	    	    	ob1[7] = "13.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周四 5-6节";
	    	    }
	    	    if(ob1[0].equals("TABLE3#5"))
	    	    {
	    	    	ob1[0]="5";
	    	    	ob1[7] = "13.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周五 5-6节";
	    	    }
	    	    if(ob1[0].equals("TABLE3#6"))
	    	    {
	    	    	ob1[0]="6";
	    	    	ob1[7] = "13.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周六 5-6节";
	    	    }
	    	    if(ob1[0].equals("TABLE3#7"))
	    	    {
	    	    	ob1[0]="7";
	    	    	ob1[7] = "13.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周日 5-6节";
	    	    }
	    	    if(ob1[0].equals("TABLE4#1"))
	    	    {
	    	    	ob1[0]="1";
	    	    	ob1[7] = "19.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周一 7-8节";
	    	    }
	    	    if(ob1[0].equals("TABLE4#2"))
	    	    {
	    	    	ob1[0]="2";
	    	    	ob1[7] = "19.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周二 7-8节";
	    	    }
	    	    if(ob1[0].equals("TABLE4#3"))
	    	    {
	    	    	ob1[0]="3";
	    	    	ob1[7] = "19.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周三 7-8节";
	    	    }
	    	    if(ob1[0].equals("TABLE4#4"))
	    	    {
	    	    	ob1[0]="4";
	    	    	ob1[7] = "19.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周四 7-8节";
	    	    }
	    	    if(ob1[0].equals("TABLE4#5"))
	    	    {
	    	    	ob1[0]="5";
	    	    	ob1[7] = "19.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周五7-8节";
	    	    }
	    	    if(ob1[0].equals("TABLE4#6"))
	    	    {
	    	    	ob1[0]="6";
	    	    	ob1[7] = "19.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周六 7-8节";
	    	    }
	    	    if(ob1[0].equals("TABLE4#7"))
	    	    {
	    	    	ob1[0]="7";
	    	    	ob1[7] = "19.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周日 7-8节";
	    	    }
	    	    if(ob1[0].equals("TABLE5#1"))
	    	    {
	    	    	ob1[0]="1";
	    	    	ob1[7] = "25.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周一 9-10节";
	    	    }
	    	    if(ob1[0].equals("TABLE5#2"))
	    	    {
	    	    	ob1[0]="2";
	    	    	ob1[7] = "25.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周二 9-10节";
	    		 }
	    	    if(ob1[0].equals("TABLE5#3"))
	    	    {
	    	    	ob1[0]="3";
	    	    	ob1[7] = "25.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周三9-10节";
	    	    }
	    	    if(ob1[0].equals("TABLE5#4"))
	    	    {
	    	    	ob1[0]="4";
	    	    	ob1[7] = "25.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周四9-10节";
	    	    	}
	    	    if(ob1[0].equals("TABLE5#5"))
	    	    {
	    	    	ob1[0]="5";
	    	    	ob1[7] = "25.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周五 9-10节";
	    	    }
	    	    if(ob1[0].equals("TABLE5#6"))
	    	    {
	    	    	ob1[0]="6";
	    	    	ob1[7] = "25.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周六 9-10节";
	    	    }
	    	    if(ob1[0].equals("TABLE5#7"))
	    	    {
	    	    	ob1[0]="7";
	    	    	ob1[7] = "25.3rem";
	    		    ob1[8] = "6rem";
	    		    ob1[9] = "周日 9-10节";
	    	    }
	    	    
        }
	    
	    
	    for(int k=0;k<list.size();k++)
	    	for(int n=0;n<list.size();n++){
	    		Object[] ob2=list.get(k);
	    		Object[] ob3=list.get(n);
	    		if(ob2[0].equals(ob3[0]) &&ob2[7].equals(ob3[7])&&k!=n)
	    		{
	    			for(int l = 1;l<7;l++)
	    			{
	    				ob2[l] = ob2[l]+"\n"+ob3[l];
	    			}
	    			list.remove(n);
	    		}
	    	}
	    
	    
	    
	    for(int i = 0;i < list.size(); i ++){
	    	  Object[] ob1 = list.get(i);//获取对象
	    	  System.out.print(ob1[0]+",");//输出数组对象中的第一个值
	    	  System.out.print(ob1[1]+",");//输出数组对象中的第一个值
	    	  System.out.print(ob1[2]+",");//输出数组对象中的第一个值
	    	  System.out.print(ob1[3]+",");//输出数组对象中的第一个值
	    	  System.out.print(ob1[4]+",");//输出数组对象中的第一个值
	    	  System.out.print(ob1[5]+",");//输出数组对象中的第一个值
	  	    System.out.print(ob1[6]+",");//输出数组对象中的第一个值
	  	  System.out.print(ob1[7]+",");//输出数组对象中的第一个值
    	  System.out.print(ob1[8]+",");//输出数组对象中的第一个值
	  	    System.out.println(ob1[9]);//输出数组对象中的第一个值

	  	   
	    }
	  //  Object[] ob1 = list.get(2); //获取到添加的第一个对象

	   
		return list;
	}

	public static void main(String[] args) {
		Jwgl_sdjzu jw = new Jwgl_sdjzu();
		try {
			//String sss=jw.yanzhengma();
			//Scanner sc = new Scanner(System.in);
			System.out.println("请输入验证码：");
			// 手动填充刚才获取的验证码的值
			//String secret = sc.next().trim();
			jw.login("201502104020", "290042","");
			//System.out.println("查询成绩测试-------");
			//jw.queryStuGrade("2015-2016", "1");
			// 查询西校区，周一，第12节空教室测试。
			// jw.queryClassroom("1", "1", "2");
		//	System.out.println("查询空教室测试------");
			//jw.queryClassroom();
			System.out.println("查询个人课表测试-------");
			jw.queryStuCourse("2014-2015", "1");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
