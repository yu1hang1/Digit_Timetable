 package com.ican.yueban.jwgl.tjpu;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
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

public class Jwgl_tjpu {
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
		   return strImg;
		    
	}
	
	public String login(String stuNumber, String password,String secret)
			throws UnsupportedOperationException, Exception {
		this.stuNumber = stuNumber;
		// 获取验证码
		
		System.out.println(secret);
		HttpPost loginPost = new HttpPost(loginUrl);// 创建登录的Post请求
		//loginPost.setHeader("Cookie", Cookie);// 带上第一次请求的Cookie
		List<NameValuePair> nameValuePairLogin = new ArrayList<NameValuePair>();// 封装Post提交参数
		
		nameValuePairLogin.add(new BasicNameValuePair("zjh1", ""));// 验证码
		nameValuePairLogin.add(new BasicNameValuePair("tips", ""));// 验证码
		nameValuePairLogin.add(new BasicNameValuePair("lx", ""));// 验证码
		nameValuePairLogin.add(new BasicNameValuePair("evalue", ""));// 验证码
		nameValuePairLogin.add(new BasicNameValuePair("eflag", ""));// 验证码
		nameValuePairLogin.add(new BasicNameValuePair("fs", ""));// 验证码
		nameValuePairLogin.add(new BasicNameValuePair("dzslh", ""));// 验证码
		nameValuePairLogin
				.add(new BasicNameValuePair("zjh", stuNumber));// 隐藏表单值
		nameValuePairLogin
				.add(new BasicNameValuePair("mm", password));// 学号
		nameValuePairLogin.add(new BasicNameValuePair("v_yzm", secret));// 密码
		//nameValuePairLogin.add(new BasicNameValuePair("submit.x", "22"));// 验证码
		//nameValuePairLogin.add(new BasicNameValuePair("submit.y","10"));// 身份,默认学生
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
				nameValuePairLogin, "GBK");
		loginPost.setEntity(entity);
		HttpResponse responseLogin = client.execute(loginPost);
		// client1.close();
		// 第三步:判断提交数据是否成功，成功返回302
		
		Header header = responseLogin.getFirstHeader("Content-Length"); 
		System.out.println("jjjj"+responseLogin.getStatusLine().getStatusCode() );
        int fileLength = Integer.valueOf(header.getValue());
		
		System.out.println("jjjj"+fileLength);
		
		if (fileLength < 1600) {
			
			
			
			
			System.out.println(fileLength);
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
						
				html = IOUtils.getHtml(is, "GB2312");
				
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
			 String s =eleCourse.get(1).text();
		        System.out.println("登录成功！欢迎您：" +s);
		        int left = s.indexOf('(');
		    	System.out.println(left);
		        int right = s.indexOf(')');
		    	System.out.println(right);
		        String n=s.substring(left+1,right);
		         
	         
			System.out.println("登录成功！欢迎您：" +n);
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
		String newQueryStuCourseUrl = "http://jwpt.tjpu.edu.cn/xkAction.do?actionType=6";
		System.out.println(Cookie);
		System.out.println(newQueryStuCourseUrl);
		
		HttpGet mainGet = new HttpGet(newQueryStuCourseUrl);
		//mainGet.setHeader("Cookie", Cookie);
		mainGet.setHeader("Referer", "http://jwpt.tjpu.edu.cn/xkAction.do?actionType=6");
		HttpResponse responseMain = client.execute(mainGet);
		InputStream is = responseMain.getEntity().getContent();
		String html = "";
		html = IOUtils.getHtml(is, "GBK");
		System.out.println(html);
		
		Pattern p = Pattern.compile("InsertSchedule(.+?);");
	    Matcher m = p.matcher(html);
	    
	   // List<Object[]>  list3 = new ArrayList<Object[]> ();//定义一个list对象
	    ArrayList<ArrayList<String>>  lists = new ArrayList<ArrayList<String>>();
	    
	
	    Document docCourse = Jsoup.parse(html);
		Elements eleCourse = docCourse.select("td");
	
		ArrayList<String> listSub=new ArrayList<String>();
		for (int i = 100; i < eleCourse.size(); i++) {
			
			 listSub.add(eleCourse.get(i).text());
			if(eleCourse.get(i).text().contains("培养方案"))
			{	listSub=new ArrayList<String>();
				System.out.println("换行");
				
			 lists.add(listSub);//将数组对象添加到list里面
			// listSub.clear();
			}
			System.out.print(i+eleCourse.get(i).text() + "	");
			
		}
		
		  List<Object[]>  list = new ArrayList<Object[]> ();//定义一个list对象
		   
		    String Day[]={"一","二","三","四","五","六","七","八","九","十"};
		System.out.println("lists");
		ArrayList<String> listSub1=new ArrayList<String>();
		for(int n=0;n<lists.size();n++)
		{ 
			//
			listSub1=lists.get(n);
			int k=(listSub1.size()-11)/7;
			for(int s=0;s<k;s++)
			{
				Object[] ob = new Object[10];//定义一个数组对象
				System.out.println(listSub1.get(11));
				for(int y = 1;y<8;y++)
				{
					if(listSub1.get(11+s*7).indexOf(y+"")!=-1)
					{
						
						ob[0]=y;
					}
				}
				 ob[1]=listSub1.get(0);
				 ob[2]=listSub1.get(1)+"_"+listSub1.get(2);
				 ob[3]=listSub1.get(6);
				
				 String str = listSub1.get(10+s*7);

			    	String reg = "[\u4e00-\u9fa5]";
			    	Pattern pat = Pattern.compile(reg);  

			    	Matcher mat=pat.matcher(str); 

			    	String repickStr = mat.replaceAll("");

			    	System.out.println("去中文后:"+repickStr);
				 
				 ob[4]=repickStr.replaceAll(",", ".");
				 ob[5]="整周";
				 ob[6]=listSub1.get(14+s*7)+listSub1.get(15+s*7)+listSub1.get(16+s*7);
				 int a;
				 for(a=0;a<10;a++)
				 {
					 if(listSub1.get(12+s*7).indexOf(Day[a])!=-1)
					 {
						 float q=(float) (3*a);
						 ob[7]=q+"rem";		
						 break;
					 }
					 
				 }
				 int e = 0;
				 for(int t=0;t<11;t++)
				 {
					 if(listSub1.get(13+s*7).indexOf(t+"")!=-1){
						 e=t;
					 }
				 }
				
				ob[8]=3*e+"rem";
				ob[9]="周"+ob[0]+listSub1.get(12+s*7)+"-"+Day[a+e-1]+"节";
				if(ob[0]!=null){
				list.add(ob);
				}
			}
			
			
		System.out.println(listSub1);
		System.out.println(k);
		
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
	  
	  //  Object[] ob1 = list.get(2);//获取到添加的第一个对象

	   
		return list;
	}

	private List<Object[]> add(Object[] ob) {
		// TODO Auto-generated method stub
		return null;
	}


	public static void main(String[] args) {
		Jwgl_tjpu jw = new Jwgl_tjpu();
		try {
			String sss=jw.yanzhengma();
			Scanner sc = new Scanner(System.in);
			System.out.println("请输入验证码：");
			// 手动填充刚才获取的验证码的值
			String secret = sc.next().trim();
			jw.login("1410210119", "100955",secret);
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
