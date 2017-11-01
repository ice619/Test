package com.chenpeng.thread;

class HelloRunner implements Runnable {
    private int i;
    private boolean stopFlag = false;

    public void run() {
        while (!stopFlag) {
            System.out.print((i++) + "  ");
            if (i > 500) i = 0;
        }
    }

    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
    }
}
/**
 * 结束线程 : 通常在线程执行体中写一些循环语句，因此控制住了循环，就相当于控制住了线程，即通知方式
 * @author Administrator
 */
public class TestThreadEnd {
	public static void main(String[] args) {
		HelloRunner r = new HelloRunner();
		Thread t = new Thread(r);
		t.start();
		for(int i = 0; i < 100; i++){
			System.out.println("--" + i);
		}
		r.setStopFlag(true);
	}
}
