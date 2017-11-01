package com.chenpeng.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

public class TestTCP02 {
	//客户端
	@Test
	public void client() throws IOException{
		String str = "长江长江，收到请回答";
		Socket socket = new Socket(InetAddress.getByName("127.0.0.1"),9898);
		//客户端向服务端发送数据
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(str.getBytes());
		
		socket.shutdownOutput();
		
		//客户端接收服务端返回来的数据
		InputStream InputStream = socket.getInputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while((len = InputStream.read(b)) != -1){
			System.out.println(new String(b, 0, len));
		}
		
		InputStream.close();
		outputStream.close();
		socket.close();
		
	}
	
	//服务端
	@Test
	public void server() throws IOException{
		ServerSocket serversocket = new ServerSocket(9898);
		Socket socket = serversocket.accept();
		//服务端接收客户端数据
		InputStream inputStream = socket.getInputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while((len = inputStream.read(b)) != -1 ){
			System.out.println(new String(b,0,len));
		}
		socket.shutdownInput();
		
		//服务端回复客户端消息
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write("长江收到，over".getBytes());
		
		outputStream.close();
		inputStream.close();
		socket.close();
		serversocket.close();
		
	}
}
