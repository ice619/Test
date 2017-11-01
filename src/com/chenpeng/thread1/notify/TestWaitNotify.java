package com.chenpeng.thread1.notify;
/*
 * 线程通信 （线程交互）: 当多个线程在完成某些任务时，多个线程之间也需要一定通信，即线程通信
 * 
 * 例如： 模拟银行账户，用户A 不断往该账户中存钱，最多存 10000 元，当存满时，通知 用户B 取钱
 * 		用户B 不断从该账户中取钱，当余额不足时，需要通知 用户A 存钱。
 * 
 * wait() : 使当前线程进入等待状态, 等待的同时释放锁的资源
 * notify() / notifyAll() : 唤醒一个/所有当前监视器下等待状态的线程,
 * 
 * 注意： wait() 和 notify() / notifyAll() 必须使用在同步中
 * 
 * 使用两个线程打印 1-100. 线程1, 线程2 交替打印
 */
public class TestWaitNotify {
	public static void main(String[] args) {
		HelloThread ht = new HelloThread();
		
		new Thread(ht).start();
		new Thread(ht).start();
	}
}

class HelloThread implements Runnable{
	
	int i = 0;

	@Override
	public void run() {
		while(true){
			
			synchronized (this) {
				
				notify();
				
				if(i <= 100){
					System.out.println(Thread.currentThread().getName() + ":" + i++);
				}
				
				try {
					wait();
				} catch (InterruptedException e) {
				}
				
			}
		}
	}

}

