package com.chenpeng.thread;
/**
 * 线程的控制：
	sleep(long millis) : 是一个静态方法，使当前执行线程进入睡眠状态
	join() / join(long millis) : 是一个实例方法，是当前执行线程进入阻塞状态 ，（调用线程等待该线程完成后，才能继续往下运行）
	interrupt() : 用于中断阻塞状态的线程,(相当于唤醒执行)
	isAlive() : 判断当前线程是否处于存活状态
	yield() : 线程让步,(谦让，使别的线程抢到cpu概率大些，但谁抢到不一定)
 */
public class TestThreadControl {
	public static void main(String[] args) throws InterruptedException {
		MyThread01 t1 = new MyThread01();
		Thread t2 = new Thread(new MyThread02());
		t1.start();
//		t2.start();//t1  t2  并行
		t1.join();
		
		/*while(t1.isAlive()){
			t1.interrupt();
		}*/
		
		t2.start();//t1结束之后t2运行
		t2.join();
		
		for (int i = 100; i < 200; i++) {
			Thread.yield();
			System.out.println(Thread.currentThread().getName() + ":" + i);
		} 
	}
}
