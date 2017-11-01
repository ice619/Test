package com.chenpeng.thread1.notify;
/*
 生产者(Productor)将产品交给店员(Clerk)，而消费者(Customer)从店员处取走产品，店员一次只能持有固定数量的产品(比如:20），
 如果生产者试图生产更多的产品，店员会叫生产者停一下，如果店中有空位放产品了再通知生产者继续生产；如果店中没有产品了，
 店员会告诉消费者等一下，如果店中有产品了再通知消费者来取走产品。
 这里可能出现两个问题：
 生产者比消费者快时，消费者会漏掉一些数据没有取到。
 消费者比生产者快时，消费者会取相同的数据。
 */
public class TestProduct {
	
	public static void main(String[] args) {
		Clerk clerk = new Clerk();
		
		Productor pro = new Productor(clerk);
		Customer cus = new Customer(clerk);
		
		new Thread(pro).start();
		new Thread(cus).start();
	}

}

class Clerk {
	private int product;

	public Clerk() {
	}

	public Clerk(int product) {
		this.product = product;
	}

	public int getProduct() {
		return product;
	}

	public void setProduct(int product) {
		this.product = product;
	}

	// 添加货物
	public synchronized void addProduct() {
		if (product < 20) {
			System.out.println("生产者生产了第" + ++product + " 个产品");
			
			notifyAll();
		} else {
			System.out.println("货物已满！");
			
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}

	// 卖货
	public synchronized void saleProduct() {
		if (product > 0) {
			System.out.println("消费者消费了第" + product-- + " 个产品");
			
			notifyAll();
		} else {
			System.out.println("缺货！");
			
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}
}

// 生产者
class Productor implements Runnable {
	private Clerk clerk;

	public Productor() {
	}

	public Productor(Clerk clerk) {
		this.clerk = clerk;
	}

	public Clerk getClerk() {
		return clerk;
	}

	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		while (true) {
			clerk.addProduct();
		}
	}

}

//消费者
class Customer implements Runnable {
	private Clerk clerk;

	public Customer() {
	}

	public Customer(Clerk clerk) {
		this.clerk = clerk;
	}

	public Clerk getClerk() {
		return clerk;
	}

	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		while(true){
			clerk.saleProduct();
		}
	}

}