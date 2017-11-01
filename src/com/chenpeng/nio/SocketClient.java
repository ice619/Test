package com.chenpeng.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClient {
	public static void main(String[] args) {
		
		Socket clientSocket = null;
		PrintWriter writer = null;
		BufferedReader reader = null;
		try {
			clientSocket = new Socket();
			clientSocket.connect(new InetSocketAddress(InetAddress.getLocalHost(),9898));
			writer = new PrintWriter(clientSocket.getOutputStream(),true);
			writer.println("Hello!");
			writer.flush();//清空缓冲区
			
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String readLine = reader.readLine();
			System.out.println("from server:" + readLine);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(writer != null){
				writer.close();
			}
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(clientSocket != null){
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}
