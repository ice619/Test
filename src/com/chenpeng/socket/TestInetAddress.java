package com.chenpeng.socket;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class TestInetAddress {
	public static void main(String[] args) throws IOException {
		/*InetAddress adress01 = InetAddress.getByName("www.baidu.com");
		System.out.println(adress01.getHostName());
		System.out.println(adress01.getHostAddress());
		
		InetAddress adress02 = InetAddress.getLocalHost();
		System.out.println(adress02);*/
		
		//---------------从某个网址下载文件---------------
//		URL url = new URL("http://127.0.0.1:8080/examples/hello.txt");
//		URL url = new URL("http://localhost:8080/Test/1.txt");
		URL url = new URL("http://localhost:8080/1.txt");
//		URL url = new URL("http://10.51.132.121:7001/CubeHRTAdminConsole/login.jsp");
		
		System.out.println(url.getProtocol());
		System.out.println(url.getHost());
		System.out.println(url.getPort());
		System.out.println(url.getPath());
		System.out.println(url.getFile());
		System.out.println(url.getRef());
		System.out.println(url.getQuery());
		
		/*InputStream in = url.openStream();
		byte[] b = new byte[1024];
		int len = 0;
		while((len = in.read(b)) != -1){
			System.out.println(new String(b, 0, len));
		}
		in.close();*/
		
		URLConnection urlc = url.openConnection();
		System.out.println(urlc.toString());
//		OutputStream os = urlc.getOutputStream("");
		FileOutputStream os = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\a.txt");
		InputStream in = urlc.getInputStream();
		byte[] b = new byte[1024];
		int len = 0;
		
		System.out.println("下载开始" + System.currentTimeMillis());
		while((len = in.read(b)) != -1){
			System.out.println(new String(b, 0, len));
			os.write(b, 0, len);
		}
		System.out.println("下载结束" + System.currentTimeMillis());
		
		in.close();
		os.close();
	}
}
