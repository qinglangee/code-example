package com.zhch.example.mina.quickstart;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;

public class SessionFilter extends IoFilterAdapter {

	@Override
	public void sessionClosed(NextFilter nextFilter, IoSession session) throws Exception {
		session.close(false);
		nextFilter.sessionClosed(session);
	}
	

    /**
     * {@inheritDoc}
     */
    public void sessionCreated(NextFilter nextFilter, IoSession session) throws Exception {
        SocketSessionConfig conf = (SocketSessionConfig)session.getConfig();
        conf.setSoLinger(0); // 可以设置 time_wait的时间
    	nextFilter.sessionCreated(session);
    }
}
