package com.chenpeng.thread;

public class TestWindow {
	public static void main(String[] args) {
		/*Window win1 = new Window();
		Window win2 = new Window();
		Window win3 = new Window();
		
		win1.setName("1号窗口");
		win2.setName("2号窗口");
		win3.setName("3号窗口");
		
		win1.start();
		win2.start();
		win3.start();*/
		Ticket ticket = new Ticket();
		Thread t1 = new Thread(ticket,"1-号窗口");
		Thread t2 = new Thread(ticket,"2-号窗口");
		Thread t3 = new Thread(ticket,"3-号窗口");
		
		t1.start();
		t2.start();
		t3.start();
		
	}
}

class Ticket implements Runnable{

	int ticket = 100;
	@Override
	public void run() {
		while(ticket > 0){
			System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --ticket);
		}
	}
}

class Window extends Thread{
	static int tick = 100;
	public void run(){
		while(tick > 0){
			System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
		}
	}
	
}
