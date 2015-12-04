package com.zhch.example.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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
			public String call() throws Exception {
				long time = new Random().nextInt(5000);
				Thread.sleep(time);
				return "time:" + time + " thread:" + Thread.currentThread().getName();
			}
		};
		List<FutureTask<String>> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			// future 会获得 callable的返回值 
			FutureTask<String> future = new FutureTask<String>(callable);
			list.add(future);

			// 在线程中启动 callable （FutureTask 实现了 Future 和 Callable 两个接口）
			new Thread(future).start();
		}

		try {
			for (FutureTask<String> future : list) {
				// future.get() 方法会一直等到 future 所在线程返回值再往下运行
				System.out.println(future.get());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		FutureExample t = new FutureExample();
		t.futureBasic();
	}

}
