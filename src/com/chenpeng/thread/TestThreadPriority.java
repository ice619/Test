package com.chenpeng.thread;

/**
 * 线程的优先级（1-10）： 默认的优先级为 5.  优先级高并不意味着线程会优先执行，只不过更多的获取 cpu 的资源。
	getPriority() : 获取线程的优先级
	setPriority() : 设置线程的优先级
	MIN_PRIORITY : 1
	NORM_PRIORITY : 5
	MAX_PRIORITY : 10
 */
public class TestThreadPriority {
	public static void main(String[] args) {
		MyThread01 t1 = new MyThread01();
		t1.setPriority(Thread.MIN_PRIORITY);
		t1.start();
		/*try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		System.out.println(t1.getName() + " 优先级为：" + t1.getPriority());
		for (int i = 10; i < 20; i++) {
			System.out.println(Thread.currentThread().getName() + " 优先级为：" + Thread.currentThread().getPriority());
		}
	}
}
