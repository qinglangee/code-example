package com.zhch.example.java.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Future对象本身可以看作是一个显式的引用，一个对异步处理结果的引用.<br>
 *
 * @author zhch
 *
 */
public class FutureExample {

	private AtomicInteger i;

	public void futureBasic() {
		// 创建一个可返回值的 Callable 实现
		Callable<String> callable = new Callable<String>() {
			@Override
            public String call() throws Exception {
				long time = new Random().nextInt(10000);
				Thread.sleep(time);
				return "time:" + time + " thread:" + Thread.currentThread().getName();
			}
		};
		List<FutureTask<String>> list = new ArrayList<>();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			// future 会获得 callable的返回值
			FutureTask<String> future = new FutureTask<>(callable);
			list.add(future);

			// 在线程中启动 callable （FutureTask 实现了 Future 和 Callable 两个接口）
			new Thread(future).start();
		}

		try {
			for (FutureTask<String> future : list) {
			    String threadResp =  future.get();
			    long getTime = System.currentTimeMillis();
				// future.get() 方法会一直等到 future 所在线程返回值再往下运行
				System.out.println((getTime - startTime) + " " + threadResp);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	// ExecutorService 执行后得到  future
	public void futureInThreadPool() {
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		Future<Integer> future = threadPool.submit(new Callable<Integer>() {
			@Override
            public Integer call() throws Exception {
			    Thread.sleep(5000);// 可能做一些事情
				return new Random().nextInt(100);
			}
		});
		threadPool.shutdown();
		try {
		    System.out.println("shut down:" + threadPool.isShutdown() + " terminate:" + threadPool.isTerminated());
		    Thread.sleep(2510);
		    System.out.println("shut down:" + threadPool.isShutdown() + " terminate:" + threadPool.isTerminated());
		    Thread.sleep(2510);
		    System.out.println("shut down:" + threadPool.isShutdown() + " terminate:" + threadPool.isTerminated());
		    Thread.sleep(2510);
		    System.out.println("shut down:" + threadPool.isShutdown() + " terminate:" + threadPool.isTerminated());
		    Thread.sleep(2510);
		    // 返回值已经在　future 中了，线程什么的关闭也没影响
		    System.out.println(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		FutureExample t = new FutureExample();
//				t.futureBasic();
		t.futureInThreadPool();
	}

}
