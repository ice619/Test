package com.chenpeng.thread1.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 模拟售票程序，实现三个窗口同时售票100张
 * 问题：模拟售票程序，实现三个窗口同时访问共享数据时，出现了 无序、重复、超额售票等多线程安全问题
 * 解决：将多个线程需要访问的共享数据包装起来，确保一次只能有一个线程执行流访问共享数据
 * 
 * Java 为上述问题提供了相应的解决办法：
 * 1. 同步代码块
 * 	 synchronized(同步监视器){
 * 		//需要访问的共享数据
 * 	 }
 * 	 同步监视器：俗称“锁”。 可以使用任意对象充当。必须确保多个线程持有同一把锁（同一个对象）
 * 2. 同步方法 ---- 隐式的锁 : this
 * 		public synchronized void show(){}
 * 3. 同步锁  Lock
 */
public class TestTicket {
	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		
		Thread win1 = new Thread(ticket, "1号窗口");
		win1.start();
		
		Thread win2 = new Thread(ticket, "2号窗口");
		win2.start();
		
		Thread win3 = new Thread(ticket, "3号窗口");
		win3.start();
	}
}

class Ticket implements Runnable{
	
	int tick = 1000;
	
	Object obj = new Object();
	
	Lock l = new ReentrantLock();

	@Override
	public void run() {
		while(true){
			
			synchronized (obj) {
				if(tick > 0){
					
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//					}
					
					System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
				}
			}
			
//			show();
			
			/*l.lock();
			
			try{
				if(tick > 0){
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//					}

					System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
				}
			} finally {
				l.unlock();
			}*/
		}
	}
	
	public synchronized void show(){
		if(tick > 0){
			
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//			}
			
			System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
		}
	}

}

