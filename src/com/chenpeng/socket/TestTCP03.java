package com.chenpeng.socket;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class TestTCP03 {
	
	@Test
	public void client() throws IOException{
		Socket socket = new Socket(InetAddress.getByName("127.0.0.1"),9898);
		FileInputStream fileInputStream = new FileInputStream("1.txt");
		OutputStream outputStream = socket.getOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while((len = fileInputStream.read(b)) != -1 ){
			outputStream.write(b,0,len);
		}
		socket.shutdownOutput();
		
		//客户端接收反馈
		InputStream inputStream = socket.getInputStream();
		while((len = inputStream.read(b)) != -1){
			System.out.println(new String(b,0,len));
		}
		
		inputStream.close();
		outputStream.close();
		fileInputStream.close();
		socket.close();
	}
	
	@Test
	public void server() throws IOException{
		ServerSocket serverSocket = new ServerSocket(9898);
		Socket socket = serverSocket.accept();
		InputStream inputStream = socket.getInputStream();
		FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
		byte[] b = new byte[1024];
		int len = 0;
		while((len = inputStream.read(b)) != -1){
			fileOutputStream.write(b, 0, len);
		}
		socket.shutdownInput();
		//回复客户端
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write("接收成功".getBytes());
		
		inputStream.close();
		outputStream.close();
		fileOutputStream.close();
		socket.close();
		serverSocket.close();
	}
}
