package com.chenpeng.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

class MyThread03 implements Runnable{

	@Override
	public void run() {
		/*for(int i = 0;i<10; i++){
			System.out.println("子线程03：" + i);
		}*/
		
		System.out.println(System.currentTimeMillis() + ":Thread ID:" + Thread.currentThread().getId());
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
}
/**
 * 线程池
 * Executors 类使用 ExecutorService  提供了一个 ThreadPoolExecutor 的简单实现
 * 但 ThreadPoolExecutor 提供的功能远不止这些
 * 
 * 【阿里强制】线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这样
	的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
	说明： Executors 返回的线程池对象的弊端如下：
	1） FixedThreadPool 和 SingleThreadPool:
	允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。
	2） CachedThreadPool 和 ScheduledThreadPool:
	允许的创建线程数量为 Integer.MAX_VALUE， 可能会创建大量的线程，从而导致 OOM
 */
public class TestThread03 {
	public static void main(String[] args) {
		/*MyThread03 task = new MyThread03();
		ExecutorService pool = Executors.newFixedThreadPool(5);
		pool.submit(task);//分配任务
		pool.shutdown();//释放任务*/	
		MyThread03 task = new MyThread03();
		ExecutorService pool = Executors.newFixedThreadPool(5);
		for(int i = 0; i<10;i++){
			pool.submit(task);//分配任务
			//pool.shutdown();//释放任务
		}
		pool.shutdown();//释放任务
	}
}
