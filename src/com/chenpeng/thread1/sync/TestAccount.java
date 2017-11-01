package com.chenpeng.thread1.sync;
/*
银行有一个账户。
有两个储户分别向同一个账户存3000元，每次存1000，存3次。每次存完打印账户余额。

问题：该程序是否有安全问题，如果有，如何解决？

【提示】
1，明确哪些代码是多线程运行代码，须写入run()方法
2，明确什么是共享数据。
3，明确多线程运行代码中哪些语句是操作共享数据的。

 */
public class TestAccount {
	
	public static void main(String[] args) {
		Account account = new Account();
		
		Customer ca = new Customer(account);
		Customer cb = new Customer(account);
		
		new Thread(ca).start();
		new Thread(cb).start();
	}
}

class Customer implements Runnable {

	private Account account;

	public Customer() {
	}

	public Customer(Account account) {
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			
			account.deposit(1000);
		}
	}

}

class Account {

	private double balance;

	public Account() {
	}

	public Account(double balance) {
		super();
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	//存款方法
	public synchronized void deposit(double amount){
		balance += amount;
		System.out.println("成功存入: " + amount + " 余额为：" + balance);
	}

}

