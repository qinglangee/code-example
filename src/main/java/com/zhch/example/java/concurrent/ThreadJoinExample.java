package com.zhch.example.java.concurrent;

public class ThreadJoinExample {
	public void test() {
		Thread t1 = new Thread(new InnerTask("t1"));
		Thread t2 = new Thread(new InnerTask("t2"));
		Thread t3 = new Thread(new InnerTask("t3"));

		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t1.start();
		t2.start();
		try {
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t3.start();

	}

	public static void main(String[] args) {
		ThreadJoinExample t = new ThreadJoinExample();
		t.test();
	}

	void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class InnerTask implements Runnable {
		public String name;

		public InnerTask(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			for (int i = 0; i < 3; i++) {
				System.out.println("I am " + name + " :" + i);
				sleep(10);
			}
		}
	}
}
