package com.chenpeng.thread1.singleton;

/**
 * 单子模式
 */
public class TestSingleton {
	public static void main(String[] args) {
		Singleton s1 = Singleton.getInstance();
		Singleton s2 = Singleton.getInstance();
		System.out.println(s1 == s2);
		
		Single one = Single.getSingle();
		Single two = Single.getSingle();
		System.out.println(one == two);
	}
}

/**
 * 懒汉式   synchronized 解决线程安全问题
 */
class Singleton{
	private static Singleton instance = null;
	private Singleton(){}
	public static Singleton getInstance(){
		if(instance == null){
			synchronized (Singleton.class) {
				if(instance == null){
					instance = new Singleton();
				}
			}
		}
		return instance;
	}
}
/**
 * 饿汉式
 */
class Single{
	private Single(){}
	private static Single onlyOne = new Single();
	public static Single getSingle(){
		return onlyOne;
	}
}