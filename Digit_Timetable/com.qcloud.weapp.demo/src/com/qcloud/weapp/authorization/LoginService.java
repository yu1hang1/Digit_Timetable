package com.qcloud.weapp.authorization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.qcloud.weapp.ConfigurationException;

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

import com.ican.yueban.jwgl.qust.DateUtils;
import com.ican.yueban.jwgl.qust.GlobalConstant;
import com.ican.yueban.jwgl.qust.IOUtils;
import com.ican.yueban.jwgl.qust.Jwgl;
import com.ican.yueban.jwgl.qust.ParseUtils;
import com.ican.yueban.jwgl.sut.Jwgl_sut;
import com.ican.yueban.jwgl.tjpu.Jwgl_tjpu;
import com.ican.yueban.jwgl.sdjzu.Jwgl_sdjzu;
import com.ican.yueban.jwgl.hblg.Jwgl_hblg;
import com.ican.yueban.jwgl.lnnu.Jwgl_lnnu;
import com.qcloud.weapp.dao.*;
import sun.misc.BASE64Encoder;
/**
 * 提供登录服务
 * */


public class LoginService {
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	
	String a="s";
	String sss;
	

	/**
	 * 从 Servlet Request 和 Servlet Response 创建登录服务
	 * @param request Servlet Request
	 * @param response Servlet Response
	 * */
	public LoginService(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		
	}
	
	
	public void writeJson(JSONObject json) {
		try {
			this.response.setContentType("application/json");
			this.response.setCharacterEncoding("utf-8");
			this.response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject prepareResponseJson() {
		JSONObject json = new JSONObject();
		try {
			json.put(Constants.WX_SESSION_MAGIC_ID, 1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject getJsonForError(Exception error, int errorCode) {
		JSONObject json = prepareResponseJson();
		try {
			json.put("code", errorCode);
			if (error instanceof LoginServiceException) {
				json.put("error", ((LoginServiceException) error).getType());
			}
			json.put("message", error.getMessage());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject getJsonForError(Exception error) {
		return getJsonForError(error, -1);
	}
	
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
	

	/**
	 * 处理登录请求
	 * @return 登录成功将返回用户信息
	 * */
	
	//返回验证码
	static Jwgl jw1 = new Jwgl();
	static	Jwgl_sut jw_sut= new Jwgl_sut();
	static	Jwgl_tjpu jw_tjpu= new Jwgl_tjpu();
	static	Jwgl_sdjzu jw_sdjzu= new Jwgl_sdjzu();
	static	Jwgl_hblg jw_hblg= new Jwgl_hblg();
	static	Jwgl_lnnu jw_lnnu= new Jwgl_lnnu();
	static	SchoolName SchoolName= new SchoolName();
	
	public UserInfo login() throws IllegalArgumentException, LoginServiceException, ConfigurationException {
	//	String code = getHeader(Constants.WX_HEADER_CODE);
	//	String encryptedData = getHeader(Constants.WX_HEADER_ENCRYPTED_DATA);
	//	String iv = getHeader(Constants.WX_HEADER_IV);
		
		//JSONObject loginResult = null;
		/*
		AuthorizationAPI api = new AuthorizationAPI();
		JSONObject loginResul
		
		try {
			loginResult = api.login(code, encryptedData, iv);
		} catch (AuthorizationAPIException apiError) {
			LoginServiceException error = new LoginServiceException("fdfdf", apiError.getMessage(), apiError);
			writeJson(getJsonForError(error));
			throw error;
		}*/
		String schoolid = request.getParameter("schoolid");
		if(schoolid.equals("qust"))
		{
			System.out.println("青岛科技大学");
			try {
				sss =jw1.yanzhengma();
				//jw.login("1303010116", "liu123456");
			} catch (UnsupportedOperationException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		}
		if(schoolid.equals("sut"))
		{
			System.out.println("沈阳工业大学");
			
			try {
				
				sss =jw_sut.yanzhengma();
				
				
			} catch (UnsupportedOperationException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		}
		if(schoolid.equals("tjpu"))
		{
			System.out.println("天津工业大学");
			
			try {
				
				sss =jw_tjpu.yanzhengma();
				
				
			} catch (UnsupportedOperationException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		}
		if(schoolid.equals("sdjzu"))
		{
			System.out.println("山东建筑大学");
			
			try {
				
				sss =jw_sdjzu.yanzhengma();
				
				
			} catch (UnsupportedOperationException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		}
		
		if(schoolid.equals("hblg"))
		{
			System.out.println("华北理工大学");
			
			try {
				
				sss =jw_hblg.yanzhengma();
				
				
			} catch (UnsupportedOperationException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		}
		if(schoolid.equals("lnnu"))
		{
			System.out.println("辽宁师范大学");
			
			try {
				
				sss =jw_lnnu.yanzhengma().replaceAll("org.apache.http.conn.EofSensorInputStream@1d225b56", "");
			System.out.println(sss);	
				
			} catch (UnsupportedOperationException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		}
		
	//测试，没用
		    System.out.print(sss+"ssss");	
		 String value = request.getParameter("name");
	     System.out .println(value);
	     //http://localhost:8080/test/servlet/RequestDemo?like=sing&like=dance
	     String likes[] = request.getParameterValues("like");
	     if(likes!=null){
	       for(String like : likes){
	         System.out.println(like);
	       }
	     }
	     //下面是这种遍历的专业写法,获取数组数据的方式（可避免空指针异常）
	     for(int i=0;likes!=null&&i<likes.length;i++){
	       System.out.println(likes[i]);
	     }
	     //获取所有名称，并根据名称获取值
	     Enumeration e1 = request.getParameterNames();
	     while(e1.hasMoreElements()){
	       String name = (String) e1.nextElement();
	       value = request.getParameter(name);
	       System.out.println(name+"="+value);
	     }
	     System.out.println("----------------------");
	     //得到request对象中用来封装数据的Map集合
	     Map<String,String[]> map = request.getParameterMap();
	     for(Entry<String, String[]> me : map.entrySet()){
	       String name = me.getKey();
	       String [] v = me.getValue();
	       System.out.println(name+"="+v[0]);
	     }		
	     System.out.println(request.getHeader("Accept-Encoding"));//
		System.out.println(request.getRequestURL());//
		//没用
		
		
		JSONObject json = prepareResponseJson();
		JSONObject session = new JSONObject();
	//	JSONObject userInfo = null;
		try {
			
			session.put("id", sss);
			session.put("skey", "ajhjabj");
			json.put("session", session);
			writeJson(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
		
		
		/*
		try {
			userInfo = loginResult.getJSONObject("user_info");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		*/
		return UserInfo.BuildFromJson(session);
	}
	//返回验证码
	
	
	//返回登陆结果
	 String rel;
	
	public UserInfo logins() throws IllegalArgumentException, LoginServiceException, ConfigurationException {
		//	String code = getHeader(Constants.WX_HEADER_CODE);
		//	String encryptedData = getHeader(Constants.WX_HEADER_ENCRYPTED_DATA);
		//	String iv = getHeader(Constants.WX_HEADER_IV);
			
			//JSONObject loginResult = null;
			/*
			AuthorizationAPI api = new AuthorizationAPI();
			JSONObject loginResul
			
			try {
				loginResult = api.login(code, encryptedData, iv);
			} catch (AuthorizationAPIException apiError) {
				LoginServiceException error = new LoginServiceException("fdfdf", apiError.getMessage(), apiError);
				writeJson(getJsonForError(error));
				throw error;
			}*/
		
	     String schoolid = request.getParameter("schoolid");
	     String xh = request.getParameter("name");
	     String mima = request.getParameter("userm");
	     String yzm = request.getParameter("yzm");
	     
	     
	     System.out.println(schoolid);
	     System.out.println(xh);
	     System.out.println(mima);
	     System.out.println(yzm);
	    if(schoolid.equals("qust")){
		
			try {
				 rel=jw1.login(xh, mima,yzm);
			} catch (UnsupportedOperationException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
						
	    	}
	    if(schoolid.equals("sut")){
	    	
	    	 try {
	    		 rel=jw_sut.login(xh, mima,yzm);
			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    }
	    if(schoolid.equals("tjpu")){
	    	System.out.println("天津工业大学");
	    	 try {
	    		 rel=jw_tjpu.login(xh, mima,yzm);
			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    }
	    if(schoolid.equals("sdjzu")){
	    	System.out.println("山东建筑大学");
	    	 try {
	    		 rel=jw_sdjzu.login(xh, mima,yzm);
			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    }
	    if(schoolid.equals("hblg")){
	    	System.out.println("华北理工大学");
	    	 try {
	    		 rel=jw_hblg.login(xh, mima,yzm);
			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    }
			
			
		    System.out.println(request.getHeader("Accept-Encoding"));//
			System.out.println(request.getRequestURL());//
			JSONObject json = prepareResponseJson();
			JSONObject session = new JSONObject();
		//	JSONObject userInfo = null;
			try {
				
				session.put("id", rel);
				session.put("skey", "ajhjabj");
				json.put("session", session);
				writeJson(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			/*
			try {
				userInfo = loginResult.getJSONObject("user_info");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			*/
			return UserInfo.BuildFromJson(session);
		}
	//返回登陆结果
	
	public UserInfo Results() throws IllegalArgumentException, LoginServiceException, ConfigurationException {
		List<Object[]> html = null;
		String schoolid = request.getParameter("schoolid");
		JSONObject json = prepareResponseJson();
		JSONObject session = new JSONObject();
		String temp = "";
		if(schoolid.equals("sut")){
		try {
			html = jw_sut.queryStuCourse("2014-2015", "1");
		
			json.put("session", html );
			writeJson(json);
				
			
		} catch (UnsupportedOperationException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}	
		
		}
		if(schoolid.equals("tjpu")){
			try {
				html = jw_tjpu.queryStuCourse("2014-2015", "1");
			
				json.put("session", html );
				writeJson(json);
					
				
			} catch (UnsupportedOperationException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}	
			
			}
		if(schoolid.equals("sdjzu")){
			try {
				html = jw_sdjzu.queryStuCourse("2014-2015", "1");
			
				json.put("session", html );
				writeJson(json);
					
				
			} catch (UnsupportedOperationException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}	
			
			}
		if(schoolid.equals("hblg")){
			try {
				html = jw_hblg.queryStuCourse("2014-2015", "1");
			
				json.put("session", html );
				writeJson(json);
					
				
			} catch (UnsupportedOperationException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}	
			
			}
		
		/*
		try {
			userInfo = loginResult.getJSONObject("user_info");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		*/
		return UserInfo.BuildFromJson(session);
		}
	

	public UserInfo schoolname() throws IllegalArgumentException, LoginServiceException, ConfigurationException {
		
		
		 List<Object[]>  list = new ArrayList<Object[]> ();//定义一个list对象
		 Object[] ob = new Object[2];//定义一个数组对象
		 List<Object[]> Schooname = null;
		JSONObject json = prepareResponseJson();
		JSONObject session = new JSONObject();
		String temp = "";
		
		
		
				try {
					Schooname=SchoolName.schoolname();
				
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					json.put("session", Schooname);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			writeJson(json);
		return UserInfo.BuildFromJson(session);
		}
	
	/**
	 * 检查当前请求的会话状态
	 * @return 如果包含可用会话，将会返回会话对应的用户信息
	 * */
	public UserInfo check() throws LoginServiceException, ConfigurationException {
		String id = getHeader(Constants.WX_HEADER_ID);
		String skey = getHeader(Constants.WX_HEADER_SKEY);
		
		AuthorizationAPI api = new AuthorizationAPI();
		JSONObject checkLoginResult = null;
		try {
			checkLoginResult = api.checkLogin(id, skey);
		} catch (AuthorizationAPIException apiError) {
			String errorType = Constants.ERR_CHECK_LOGIN_FAILED;
			if (apiError.getCode() == 60011 || apiError.getCode() == 60012) {
				errorType = Constants.ERR_INVALID_SESSION;
			}
			LoginServiceException error = new LoginServiceException(errorType, apiError.getMessage(), apiError);
			writeJson(getJsonForError(error));
			throw error;
		}
		JSONObject userInfo = null;
		try {
			userInfo = checkLoginResult.getJSONObject("user_info");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return UserInfo.BuildFromJson(userInfo);
	}
	
	private String getHeader(String key) throws LoginServiceException {
		String value = request.getHeader(key);
		if (value == null || value.isEmpty()) {
			LoginServiceException error = new LoginServiceException("INVALID_REQUEST", String.format("请求头不包含 %s，请配合客户端 SDK 使用", key));
			writeJson(getJsonForError(error));
			throw error;
		}
		return value;
	}
	
}
