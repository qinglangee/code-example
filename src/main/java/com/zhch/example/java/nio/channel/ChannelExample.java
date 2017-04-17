package com.zhch.example.java.nio.channel;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class ChannelExample {

	public void test() throws IOException {

		SocketChannel socketChannel = SocketChannel.open();
	}

	public static void main(String[] args) throws IOException {
		ChannelExample t = new ChannelExample();
		t.test();
	}
}
