package com.zhch.example.mina.quickstart;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaTimeServer {
	public static int PORT = 12222;
	public static void main(String[] args) throws IOException, InterruptedException {
		// new 一个接口
		IoAcceptor acceptor = new NioSocketAcceptor();

		// 添加过滤器
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
//		acceptor.getFilterChain().addLast("mysession", new SessionFilter());
		
		// 设置handler, 这是主要的处理逻辑所在
		acceptor.setHandler(  new TimeServerHandler() );
		

		// 配置acceptor
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );
        
        // 绑定端口号
        acceptor.bind( new InetSocketAddress(PORT) );
        
//        for(int i=0;i<20;i++){
//        	Thread.sleep(1000);
//        	Map<Long, IoSession> map = acceptor.getManagedSessions();
//        	for(Map.Entry<Long, IoSession> e : map.entrySet()) {
//        		System.out.println("close :" + e.getValue().close(true));
//        	}
//        }
	}
}