package com.zhch.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {
	long start = System.currentTimeMillis();

	public void test() {
		ExecutorService pool = Executors.newCachedThreadPool();
		
		// Executors.newCachedThreadPool() 的实现如下
		// 第一个参数 coolPoolSize 是一直放在池中的线程数量, 设为0 会一直只有一个线程在工作, why?
		new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
			new SynchronousQueue<Runnable>());

		// 这个是单线程的线程池, 任务一个一个来
		// ExecutorService pool = Executors.newSingleThreadExecutor();

		MyThread t1 = new MyThread();
		MyThread t2 = new MyThread();
		MyThread t3 = new MyThread();
		// 将线程放入池中进行执行
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
	}

	public static void main(String[] args) {
		ThreadPoolExample t = new ThreadPoolExample();
		t.test();
	}

	class MyThread implements Runnable {

		public void run() {
			for (int i = 0; i < 3; i++) {
				try {
					Thread.sleep(500);
				} catch (Exception e) {

				}
				System.out.println(System.currentTimeMillis() - start);
			}
		}

	}
}
