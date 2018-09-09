 package com.ican.yueban.jwgl.qust;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;
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

public class Jwgl {
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
	CloseableHttpClient client = HttpClients.createDefault();
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
		
		CloseableHttpResponse responseSecret = client.execute(secretCodeGet);
		// 获取返回的Cookie
		//Cookie = responseSecret.getFirstHeader("Set-Cookie").getValue();
		System.out.println(Cookie);
	
		// 将验证码下载到C盘
		IOUtils.getSecret(responseSecret.getEntity().getContent(),
				"secretCode.png", "d://");
		  String strImg = getImageStr("d:/secretCode.png");
		  
		   System.out.println(strImg);
		   return strImg;
		    
	}
	
	
	public String login(String stuNumber, String password,String yzm)
			throws UnsupportedOperationException, Exception {
		this.stuNumber = stuNumber;
		// 获取验证码
		String viewState = IOUtils.getViewState(indexUrl, "", "");
		Scanner sc = new Scanner(System.in);
	//	System.out.println("请输入验证码");
		// 手动填充刚才获取的验证码的值
		//String secret = sc.next().trim();
		HttpPost loginPost = new HttpPost(loginUrl);// 创建登录的Post请求
		//loginPost.setHeader("Cookie", Cookie);// 带上第一次请求的Cookie
		List<NameValuePair> nameValuePairLogin = new ArrayList<NameValuePair>();// 封装Post提交参数
		nameValuePairLogin
				.add(new BasicNameValuePair("__VIEWSTATE", viewState));// 隐藏表单值
		nameValuePairLogin
				.add(new BasicNameValuePair("txtUserName", stuNumber));// 学号
		nameValuePairLogin.add(new BasicNameValuePair("TextBox2", password));// 密码
		nameValuePairLogin.add(new BasicNameValuePair("txtSecretCode", yzm));// 验证码
		nameValuePairLogin.add(new BasicNameValuePair("RadioButtonList1",
				identityStu));// 身份,默认学生
		nameValuePairLogin.add(new BasicNameValuePair("Button1", ""));
		nameValuePairLogin.add(new BasicNameValuePair("lbLanguage", ""));
		nameValuePairLogin.add(new BasicNameValuePair("hidPdrs", ""));
		nameValuePairLogin.add(new BasicNameValuePair("hidsc", ""));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
				nameValuePairLogin, "GB2312");
		loginPost.setEntity(entity);
		HttpResponse responseLogin = client.execute(loginPost);
		// client1.close();
		// 第三步:判断提交数据是否成功，成功返回302
		if (responseLogin.getStatusLine().getStatusCode() == 302) {
			// 如果提交成功，带着Cookie请求重定向的main页面，并获取学生姓名
			HttpGet mainGet = new HttpGet(mainUrl + stuNumber);
			//mainGet.setHeader("Cookie", Cookie);
			mainGet.setHeader("Referer", loginUrl);
			HttpResponse responseMain = client.execute(mainGet);
			InputStream is = responseMain.getEntity().getContent();
			String html = "";
			try {
				html = IOUtils.getHtml(is, "GB2312");
				//System.out.println(html);
			} catch (Exception e) {
				System.out.println("解析html失败！");
				e.printStackTrace();
			}
			stuName = Jsoup.parse(html).getElementById("xhxm").text();
			System.out.println("登录成功！欢迎您：" + stuName);
			client.close();
			return "登录成功！欢迎您：" + stuName;
		} else {
			System.out.println("登录失败！");
			client.close();
			return "登录失败！";
		}

	}

	

	/**
	 * 查询个人课表方法
	 * 
	 * @param xnd
	 * @param xqd
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void queryStuCourse(String xnd, String xqd)
			throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		String newQueryStuCourseUrl = queryStuCourseUrl + stuNumber + "&xm="
				+ "%C1%F5%EA%C9%C9%AD" + queryStuCourseGnmkd;
		System.out.println(Cookie);
		System.out.println(newQueryStuCourseUrl);
		String viewState = IOUtils.getViewState(newQueryStuCourseUrl, Cookie,
				mainUrl + stuNumber);
		System.out.println(viewState);
		HttpPost queryStuCoursePost = new HttpPost(newQueryStuCourseUrl);
		List<NameValuePair> stuCoursePair = new ArrayList<NameValuePair>();
		stuCoursePair.add(new BasicNameValuePair("__EVENTTARGET", "xnd"));
		stuCoursePair.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		stuCoursePair.add(new BasicNameValuePair("__VIEWSTATE", viewState));
		stuCoursePair.add(new BasicNameValuePair("xnd", xnd));
		stuCoursePair.add(new BasicNameValuePair("xqd", xqd));
		UrlEncodedFormEntity entitySource = new UrlEncodedFormEntity(
				stuCoursePair);
		queryStuCoursePost.setEntity(entitySource);
		queryStuCoursePost.setHeader("Cookie", Cookie);
		System.out.println(Cookie);
		queryStuCoursePost.setHeader("Referer", mainUrl + stuNumber);
		HttpResponse responseStuCourse = client.execute(queryStuCoursePost);
		String html = IOUtils.getHtml(responseStuCourse.getEntity()
				.getContent(), "GB2312");
		System.out.println(html);
		Document docCourse = Jsoup.parse(html);
		Elements eleCourse = docCourse.select("td");
		for (int i = 2; i < eleCourse.size(); i++) {
			System.out.print(eleCourse.get(i).text() + "	");
			if (i % 9 == 0) {
				System.out.println();
			}
		}
	}

	public static void main(String[] args) {
		Jwgl jw = new Jwgl();
		try {
			String sss=jw.yanzhengma();
			jw.login("1303010116", "liu123456","");
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
