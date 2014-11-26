package com.zhch.example.mina.quickstart;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class TimeClient {
	static String host = "127.0.0.1";
	static int port = 12222;

	public static void main(String[] args) throws InterruptedException {
		// 新建 connector
		NioSocketConnector connector = new NioSocketConnector();
		// 设置处理程序 handler
		connector.setHandler(new TimeClientHanlder());
		
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();

		// 设置 filter
		TextLineCodecFactory tlcf = new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.UNIX, LineDelimiter.UNIX);
		tlcf.setDecoderMaxLineLength(Integer.MAX_VALUE);
		tlcf.setEncoderMaxLineLength(Integer.MAX_VALUE);
		chain.addLast("codec", new ProtocolCodecFilter(tlcf));
		chain.addLast("mysession", new SessionFilter());
		
		// 连接服务器
		ConnectFuture future = connector.connect(new InetSocketAddress(host, port));
		future.awaitUninterruptibly(10000);
		
		// 取得session , 写数据
		IoSession session = future.getSession();
		for(int i=0;i<3;i++){
			session.write("abc");
		}
		Thread.sleep(5000);
		session.close(true);
	}
}
