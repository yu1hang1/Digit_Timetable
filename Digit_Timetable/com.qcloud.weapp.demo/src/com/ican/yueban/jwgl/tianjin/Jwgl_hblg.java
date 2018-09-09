package com.ican.yueban.jwgl.tianjin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
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
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.cookie.Cookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.ican.yueban.jwgl.sdjzu.IOUtils;

import sun.misc.BASE64Encoder;

public class Jwgl_hblg {
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
	DefaultHttpClient client;
	public String yanzhengmat;
	/**
	 * 登录功能
	 * 
	 * @param stuNumber
	 * @param password
	 * @return
	 * @throws Exception
	 * @throws UnsupportedOperationException
	 */
	
	//输入图片的路径，然后输出一个字符串
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
	
	
	 public static String txt2String(File file){
	        StringBuilder result = new StringBuilder();
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	            String s = null;
	            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	                result.append(System.lineSeparator()+s);
	            }
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        return result.toString();
	    }
	
	public String yanzhengma()throws UnsupportedOperationException, Exception{
		HttpGet secretCodeGet = new HttpGet("http://public.tj.hrss.gov.cn/uaa/captcha/img");
		client    = new DefaultHttpClient();
		
		CloseableHttpResponse responseSecret = client.execute(secretCodeGet);
		System.out.println("This is Cookies " + Cookie);
	//	String viewState = IOUtils.getViewState(indexUrl, "", "");
		// 将验证码下载到C盘
		Calendar now = Calendar.getInstance();
		int second = now.get(Calendar.SECOND);
		int mm = now.get(Calendar.MILLISECOND);
		String name = ""+second+mm;
		
		IOUtils.getSecret(responseSecret.getEntity().getContent(),
				name+".txt", "d://");
		
		File file = new File("d:/"+name+".txt");
		String yanzhengmatemp = txt2String(file);
		  int left = yanzhengmatemp.indexOf(':');
	    	System.out.println(left);
	        int right = yanzhengmatemp.indexOf('}');
	        yanzhengmat=yanzhengmatemp.substring(left+2,right-1);
        System.out.println(yanzhengmat);
        
        HttpGet secretCodeGett = new HttpGet("http://public.tj.hrss.gov.cn/uaa/captcha/img/"+yanzhengmat);
	//	client =  HttpClients.createDefault();
		CloseableHttpResponse responseSecrett = client.execute(secretCodeGett);
		System.out.println("This is Cookies " + Cookie);
		
		// 将验证码下载到C盘
		 now = Calendar.getInstance();
		 second = now.get(Calendar.SECOND);
		 mm = now.get(Calendar.MILLISECOND);
		 name = ""+second+mm;
		
		IOUtils.getSecret(responseSecrett.getEntity().getContent(),
				name+".png", "d://");
		  String strImg = getImageStr("d:/"+name+".png");
	
		   System.out.println(strImg);
		 
	
		   System.out.println(strImg);
		   return strImg;
		    
	}
	
	public String login(String stuNumber, String password,String secret)
			throws UnsupportedOperationException, Exception {
		this.stuNumber = stuNumber;
		// 获取验证码
		
		System.out.println(secret);
		HttpPost loginPost = new HttpPost("http://public.tj.hrss.gov.cn/uaa/api/person/idandmobile/login");// 创建登录的Post请求
		//loginPost.setHeader("Cookie", Cookie);// 带上第一次请求的Cookie
		 List<Cookie> cookies = client.getCookieStore().getCookies();
		 if (cookies.isEmpty()) {
	            System.out.println("None165");
	        } else {
	            for (int i = 0; i < cookies.size(); i++) {
	            	  System.out.println("yes");
	                System.out.println("- " + cookies.get(i).toString());
	            }
	        }
		List<NameValuePair> nameValuePairLogin = new ArrayList<NameValuePair>();// 封装Post提交参数
		
		nameValuePairLogin
				.add(new BasicNameValuePair("username", stuNumber));// 隐藏表单值
		nameValuePairLogin
				.add(new BasicNameValuePair("password", password));// 学号
		nameValuePairLogin
		.add(new BasicNameValuePair("captchaId", yanzhengmat));// 学号
		nameValuePairLogin.add(new BasicNameValuePair("captchaWord", secret));// 密码
		//nameValuePairLogin.add(new BasicNameValuePair("submit.x", "22"));// 验证码
		//nameValuePairLogin.add(new BasicNameValuePair("submit.y","10"));// 身份,默认学生
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
				nameValuePairLogin, "GBK");
		loginPost.setEntity(entity);
		System.out.println("Runs here");
		HttpResponse responseLogin = client.execute(loginPost);
		 List<Cookie> cookies1 = client.getCookieStore().getCookies();
		 if (cookies1.isEmpty()) {
	            System.out.println("None190");
	        } else {
	            for (int i = 0; i < cookies1.size(); i++) {
	            	  System.out.println("yes194");
	                System.out.println("- " + cookies1.get(i).toString());
	            }
	        }
		 EntityUtils.consume(responseLogin.getEntity()); 
		Cookie = responseLogin.getFirstHeader("Set-Cookie").getValue();
		System.out.println(Cookie);
HttpPost loginPost13 = new HttpPost("http://public.tj.hrss.gov.cn/ehrss/si/person/ui/?code=BgBzdM");
		
	
		
		System.out.println("This is Referer");
		HttpResponse responseMain3 = client.execute(loginPost13);
		 EntityUtils.consume(responseMain3.getEntity()); 
		 List<Cookie> cookies23 = client.getCookieStore().getCookies();
		 if (cookies23.isEmpty()) {
	            System.out.println("None217");
	        } else {
	            for (int i = 0; i < cookies23.size(); i++) {
	            	  System.out.println("yes220");
	                System.out.println("- " + cookies23.get(i).toString());
	            }
	        }
		HttpPost loginPost1 = new HttpPost("http://public.tj.hrss.gov.cn/ehrss/si/person/ui/?code=b7nV1i#/home");
		
	
		
		System.out.println("This is Referer");
		HttpResponse responseMain = client.execute(loginPost1);
		 EntityUtils.consume(responseMain.getEntity()); 
		 List<Cookie> cookies2 = client.getCookieStore().getCookies();
		 if (cookies2.isEmpty()) {
	            System.out.println("None217");
	        } else {
	            for (int i = 0; i < cookies2.size(); i++) {
	            	  System.out.println("yes220");
	                System.out.println("- " + cookies2.get(i).toString());
	            }
	        }
		 HttpPost loginPost12 = new HttpPost("http://public.tj.hrss.gov.cn/login?code=2v6kQq&state=zOjYwk");
			
			
		 loginPost12.setHeader("Cookie", "SESSION=6e6fa070-f0a8-4509-97c9-fd04f07276a6; JSESSIONID=B4416AA060B7A152BB9F921D1A9DBAD4");
			System.out.println("This is Referer");
			HttpResponse responseMain2 = client.execute(loginPost12);
			 EntityUtils.consume(responseMain2.getEntity()); 
			 List<Cookie> cookies22 = client.getCookieStore().getCookies();
			 if (cookies22.isEmpty()) {
		            System.out.println("None217");
		        } else {
		            for (int i = 0; i < cookies22.size(); i++) {
		            	  System.out.println("yes220");
		                System.out.println("- " + cookies22.get(i).toString());
		            }
		        }
		System.out.println(responseMain);
	//	Cookie = responseMain.getFirstHeader("Set-Cookie").getValue();
		
		
		//client1.close();
		
		Header header = responseLogin.getFirstHeader("Location"); 
		System.out.println("sssss"+header);
		System.out.println(responseLogin);
		

		
		if (header.toString().indexOf("idandmobile")!=-1) {
			
			
			
			
			
			System.out.println("登录成功！欢迎您");
			//client.close();
			return "yes";
		} else { 
			System.out.println("登录失败！");
			//client.close();
			return "no";
		}

	}

	

	//查询
	public int queryStuCourse(String xnd, String xqd)
			throws ClientProtocolException, IOException {
		
		
		//System.out.println( cookiestore.);
		String newQueryStuCourseUrl = "http://public.tj.hrss.gov.cn/ehrss/si/person/ui/?code=BgBzdM#/rights/payment/employeePayment";
		
		
		System.out.println(newQueryStuCourseUrl);
		
		HttpGet mainGet = new HttpGet(newQueryStuCourseUrl);

		//HttpGet secretCodeGet = new HttpGet("http://public.tj.hrss.gov.cn/ehrss/si/person/ui/?code=bn6x5X#/rights/payment/employeePayment");
		
		
		
		//mainGet.setHeader("Cookie", "JSESSIONID=5E657010626255C2774FC2E277008C78; SESSION=486959b5-81e5-47a3-993d-21efb584fcc8");
		mainGet.setHeader("Cookie", "JSESSIONID=3040AD24C5AD0A6DE94BF606D371BB76; SESSION=545a17e1-6702-4d1d-b543-a943d578d229");
		//mainGet.setHeader("Referer", "http://60.2.249.144/xkAction.do?actionType=6");
		System.out.println("This is Referer");
		HttpResponse responseMain = client.execute(mainGet);
		 List<Cookie> cookies = client.getCookieStore().getCookies();
		 if (cookies.isEmpty()) {
	            System.out.println("None279");
	        } else {
	            for (int i = 0; i < cookies.size(); i++) {
	            	  System.out.println("yes282");
	                System.out.println("- " + cookies.get(i).toString());
	            }
	        }
		
		InputStream is = responseMain.getEntity().getContent();
		String html = "";
		html = IOUtils.getHtml(is, "UTF-8");
		System.out.println("htmlasdsfdf");
		System.out.println(html);
		System.out.println("html");
		

	   
		return 1;
	}

	private List<Object[]> add(Object[] ob) {
		// TODO Auto-generated method stub
		return null;
	}


	public static void main(String[] args) {
		Jwgl_hblg jw = new Jwgl_hblg();
		try {
			String sss=jw.yanzhengma();
			Scanner sc = new Scanner(System.in);
			System.out.println("请输入验证码：");
			// 手动填充刚才获取的验证码的值
			String secret = sc.next().trim();
			jw.login("410421199106174019", "170691",secret);
			//System.out.println("查询成绩测试-------");
			//jw.queryStuGrade("2015-2016", "1");
			// 查询西校区，周一，第12节空教室测试。
			// jw.queryClassroom("1", "1", "2");
		//	System.out.println("查询空教室测试------");
			//jw.queryClassroom();
			System.out.println("查询-------");
			jw.queryStuCourse("2016-2017", "2");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
