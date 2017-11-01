package com.chenpeng.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;
/**
 * 1.客户端发送内容给服务端，服务端将内容打印到控制台上。
 * @author Administrator
 *
 */
public class TestTCP01 {

	//客户端
	@Test
	public void client(){
		String str = "客户端发送数据给服务端......";
		Socket socket = null;
		OutputStream OutputStream = null;
		try {
			socket = new Socket(InetAddress.getByName("127.0.0.1"),9898);
			OutputStream = socket.getOutputStream();
			OutputStream.write(str.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(OutputStream != null){
				try {
					OutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
	}
	
	//服务端
	@Test
	public void server(){
		ServerSocket serverSocket = null;
		Socket socket = null;
		InputStream inputStream = null;
		try {
			serverSocket = new ServerSocket(9898);
			socket = serverSocket.accept();
			inputStream = socket.getInputStream();
			
			byte[] b = new byte[1024];
			int len = 0;
			while((len = inputStream.read(b)) != -1){
				System.out.println(new String(b, 0, len));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(serverSocket != null){
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
