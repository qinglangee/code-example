package com.zhch.example.java;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueDemo {
	public void commonQueue() {
		System.out.println("common queue====");
		Queue<Integer> queue = new LinkedBlockingQueue<Integer>();
		for (int i = 0; i < 10; i++) {
			queue.add(i);
		}
		for (int i = 0; i < 10; i++) {
			System.out.println(queue.poll());
		}

	}

	public void concurrentQueue() {
		Queue<Integer> linkQueue = new ConcurrentLinkedQueue<>();

	}

	public static void main(String[] args) {
		QueueDemo t = new QueueDemo();
		t.commonQueue();
	}
}
