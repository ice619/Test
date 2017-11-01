package com.chenpeng.thread;

class MyThread02 implements Runnable{

	@Override
	public void run() {
		for(int i = 0;i<10; i++){
			System.out.println("子线程02：" + i);
		}
	}
}
/**
 * 实现Runnable
 * 1）避免了单继承的局限性
	2）多个线程可以共享同一个接口子类的对象，非常适合多个相同线程来处理同一份资源。
 */
public class TestThread02 {
	public static void main(String[] args) {
		
	//MyThread02 t02 = new MyThread02();
	//Thread t = new Thread(t02);
	Thread t = new Thread(new MyThread02());
	t.start();
	}
}
