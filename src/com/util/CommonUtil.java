package com.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

//import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 * 通用工具类
 *
 */
public class CommonUtil {
	
	private static Logger log = Logger.getLogger(CommonUtil.class);
	
	/**
	 * 验证手机号是否合法
	 * 
	 * @param phone 需要被验证的手机号
	 * 
	 * @return 合法则返回true，不合法返回false
	 */
	public static boolean checkPhone(String phone) {
		
		
		String regExp = "^([1][3,4,5,7,8][0-9]{9})|(61[0-9]{9})$";
		
		return phone.matches(regExp);
	}
	
	/**
	 * 发送post请求
	 * 
	 * @author Xyf
	 * 
	 * @param url 发送请求的url
	 * 
	 * @param params 请求参数
	 * 
	 * @return 返回请求后获取的数据
	 * 
	 * @exception 出错抛出异常
	 */
	public static String sendPost(String url, String params) throws Exception {
		
		URLConnection conn = null;
		OutputStream os = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer(); //存储请求返回的参数
		
		try {
			URL requestUrl = new URL(url);
			
			conn = requestUrl.openConnection(); //打开和URL之间的连接
			
			conn.setRequestProperty("accept", "*/*"); //设置通用的请求属性
			conn.setRequestProperty("connection", "close");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			conn.setConnectTimeout(30000); // 设置连接主机超时
			conn.setReadTimeout(30000); // 设置从主机读取数据超时
			
			conn.setDoOutput(true); //发送post请求必须设置
			conn.setDoInput(true);
			
			os = conn.getOutputStream(); //获取 URLConnection 对象的输出流
			
			os.write(params.getBytes("UTF-8"));
			
			in = new BufferedReader(new InputStreamReader(conn.getInputStream())); //获取 URLConnection 对象的输入流
			
			String line;
			while((line = in.readLine()) != null) {
				result.append(line);
			}
			
		} catch (Exception e) {
			log.info("post请求发生失败……");
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			if (os != null) {
				os.close();
			}
			if (in != null) {
				in.close();
			}
		}
		
		log.info("post请求返回参数---" + result.toString());
		
		return result.toString();
	}
	
	/**
	 * 发送get请求
	 * 
	 * @param url 发送请求的url，带请求参数的url，格式如：http://www.baidu.com?a=skdfj&b=dlkj
	 * 
	 * @return 返回请求后获取的数据
	 * 
	 * @throws Exception 出错抛出异常
	 */
	public static String sendGet(String url) throws Exception {
		
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer(); //存储请求返回的参数
		
		try {
			
			URL requestUrl = new URL(url);
			
			URLConnection conn = requestUrl.openConnection(); //打开和URL之间的连接
			
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "close");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			conn.setConnectTimeout(30000); // 设置连接主机超时
			conn.setReadTimeout(30000); // 设置从主机读取数据超时
			
			conn.connect(); //建立实际连接
			
            Map<String, List<String>> map = conn.getHeaderFields(); // 获取所有响应头字段
            
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
            String line;
            while ((line = in.readLine()) != null) {
            	result.append(line);
            }
            
		} catch (Exception e) {
			log.error("get 请求发生错误", e);
			throw e;
		} finally {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		}
		
		log.info("get请求返回参数->" + result.toString());
		
		return result.toString();
	}
	
	/**
	 * 获取uuid
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
	
}
