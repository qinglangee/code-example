package com.zhch.example.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class CountDownLatchExample {
	static int threadCount = 10;

	public void test() {
		AtomicInteger total = new AtomicInteger(0);
		// 先定义一个 latch
		CountDownLatch latch = new CountDownLatch(threadCount);

		// 再启动相同个数线程， 每个调用 latch 减一
		for (int i = 0; i < threadCount; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 10000; j++) {
						total.incrementAndGet();
					}
					latch.countDown();
				}
			}).start();
		}

		try {
			System.out.println("await==== :" + total);
			latch.await(); // await() 会等到 latch 减到0才会继续往下执行
			System.out.println("total: " + total);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		CountDownLatchExample t = new CountDownLatchExample();
		t.test();
	}
}
