package com.chenpeng.thread1.notify;

import com.chenpeng.thread1.notify.Account;
import com.chenpeng.thread1.notify.CustomerA;
import com.chenpeng.thread1.notify.CustomerB;
/**
 * 存取款
 */
public class TestAccount {
	public static void main(String[] args) {
		
		Account account = new Account();
		CustomerA ca = new CustomerA(account);
		CustomerB cb = new CustomerB(account);
		
		new Thread(ca).start();
		new Thread(cb).start();
	}
}

class Account {

	private double balance;

	public Account() {
	}

	public Account(double balance) {
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
		if(balance + amount <= 10000){
			
			notifyAll();
			
			balance += amount;
			System.out.println("**成功存入：" + amount + " 余额为：" + balance);
		}else{
			System.out.println("账户已满！");
			
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}
	
	//取款方法
	public synchronized void withdraw(double amount){
		if(balance >= amount){
			notifyAll();
			
			balance -= amount;
			System.out.println("--成功取出：" + amount + " 余额为：" + balance);
		}else{
			System.out.println("余额不足！");
			
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}

}

class CustomerA implements Runnable {

	private Account account;

	public CustomerA(Account account) {
		this.account = account;
	}

	public CustomerA() {
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public void run() {
		while(true){
			
			try {
				Thread.sleep((int)(Math.random() * 100));
			} catch (InterruptedException e) {
			}
			
			account.deposit(1000);
		}
	}

}

class CustomerB implements Runnable {

	private Account account;

	public CustomerB(Account account) {
		this.account = account;
	}

	public CustomerB() {
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public void run() {
		while(true){
			
			try {
				Thread.sleep((int)(Math.random() * 50));
			} catch (InterruptedException e) {
			}
			
			account.withdraw(1000);
		}
	}

}

