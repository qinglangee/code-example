package com.zhch.example.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolLearn {
	class SleepThread implements Runnable {
		@Override
		public void run() {
			try {
				Thread.sleep(3000);
				System.out.println(Thread.currentThread().getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void corePoolSize() {
		int corePoolSize = 2;
		int maxPoolSize = 10;
		ExecutorService pool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.AbortPolicy());
		for (int i = 0; i < 29; i++) {
			pool.execute(new Thread(new SleepThread(), "Thread " + i));
		}
		pool.shutdown();
	}

	public static void main(String[] args) {
		ThreadPoolLearn t = new ThreadPoolLearn();
		int test = 1;
		switch (test) {
		case 1:
			t.corePoolSize();
			break;
		}
	}
}
