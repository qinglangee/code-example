package com.zhch.example.mina.quickstart;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TimeServerHandler extends IoHandlerAdapter {
	// 错误处理函数
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	// 消息处理函数 
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String str = message.toString();
		if (str.trim().equalsIgnoreCase("quit")) {
			session.close();
			return;
		}

		Date date = new Date();
		session.write(date.toString());
		System.out.println("Message written...");
	}

	// session空闲 
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println("IDLE " + session.getIdleCount(status));
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("session close::" + session.getId());
        session.close(true);
    }
}