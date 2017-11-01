package com.chenpeng.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

class MyThread04 implements Callable{
	@Override
	public Integer call() throws Exception {
		int sum = 0;
		for(int i = 0;i<10; i++){
			//System.out.println("子线程03：" + i);
			sum += i;
		}
		return sum;
	}
}
/**
 * 实现Callable,可以获取执行结果，其他几种方式不行
 * 可以看出RunnableFuture继承了Runnable接口和Future接口，而FutureTask实现了RunnableFuture接口。
 * 所以它既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值。
 */
public class TestThread04 {
	public static void main(String[] args) {
		
		//第二种方式
		ExecutorService pool = Executors.newFixedThreadPool(5);
		MyThread04 task = new MyThread04();
		FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
		pool.submit(futureTask);
		pool.shutdown();
		
		//第一种方式
		/*MyThread04 task = new MyThread04();
		FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
		Thread thread = new Thread(futureTask);
		thread.start();
		*/
		try {
			System.out.println("task运行结果是"+futureTask.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}

