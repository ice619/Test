package com.chenpeng.thread;

class MyThread01 extends Thread{
	//重写run方法
	public void run(){
		/*try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		for(int i = 0;i<10; i++){
			System.out.println("子线程01：" + i);
		}
	}
}
/**
 * 继承Thread
 */
public class TestThread01 {
	public static void main(String[] args) {
		MyThread01 t = new MyThread01();
		t.start();
	}
}
