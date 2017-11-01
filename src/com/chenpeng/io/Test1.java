package com.chenpeng.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test1 {
	public static void main(String[] args) {
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
//			FileInputStream fis = new FileInputStream("C:\\Users\\Administrator\\Desktop\\a.avi");
			FileInputStream fis = new FileInputStream("C:\\Users\\Administrator\\Desktop\\a.txt");
			bis = new BufferedInputStream(fis);
			
//			FileOutputStream fos = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\b.avi");
			FileOutputStream fos = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\b.txt");
			bos = new BufferedOutputStream(fos);
			
			byte[] b = new byte[4096];
			int len = 0;
			try {
				System.out.println("复制开始" + System.currentTimeMillis());
				while((len = bis.read(b)) != -1){
					bos.write(b, 0, len);
				}
				System.out.println("复制结束" + System.currentTimeMillis());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
