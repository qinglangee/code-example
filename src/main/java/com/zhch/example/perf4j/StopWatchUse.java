package com.zhch.example.perf4j;

import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;

/**
 * Develop Guide
 * http://perf4j.codehaus.org/devguide.html
 */
public class StopWatchUse {
	/**
	 * 最简单的
	 * @throws InterruptedException
	 */
	public void simple() throws InterruptedException {
		// 创建stopWatch, 加个标签
		StopWatch stopWatch = new Slf4JStopWatch("abcde");
		Thread.sleep((long)(Math.random() * 1000L));
		// 停止,并打印
		stopWatch.stop();
	}
	
	/**
	 * 根据条件在stop时打印不同的tag
	 */
	public void stopWithTag(){
		StopWatch stopWatch = new Slf4JStopWatch();

		try {
		    long sleepTime = (long)(Math.random() * 1000L);
		    Thread.sleep(sleepTime);
		    if (sleepTime > 500L) {
		        throw new Exception("Throwing exception");
		    }
		    stopWatch.stop("codeBlock2.success", "Sleep time was < 500 ms");
		} catch (Exception e) {
		    stopWatch.stop("codeBlock2.failure", "Exception was: " + e);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		StopWatchUse t = new StopWatchUse();
		for(int i=0;i<2;i++){
			t.stopWithTag();
		}
	}
}
