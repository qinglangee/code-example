package com.zhch.example.ssh.tunnelexample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * 
 * Java Tip 111: Implement HTTPS tunneling with JSSE<br>
 * http://www.javaworld.com/article/2077475/core-java/java-tip-111--implement-
 * https-tunneling-with-jsse.html
 * 
 * @author zhch
 *
 */
public class SSLTunnelSocketFactory extends SSLSocketFactory {
	String tunnelHost;
	int tunnelPort;
	SSLSocketFactory dfactory;

	public SSLTunnelSocketFactory(String proxyhost, String proxyport) {
		tunnelHost = proxyhost;
		tunnelPort = Integer.parseInt(proxyport);
		dfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
	}

	public Socket createSocket(Socket s, String host, int port, boolean autoClose)
			throws IOException, UnknownHostException {

		Socket tunnel = new Socket(tunnelHost, tunnelPort);

		doTunnelHandshake(tunnel, host, port);

		SSLSocket result = (SSLSocket) dfactory.createSocket(tunnel, host, port, autoClose);

		result.addHandshakeCompletedListener(new HandshakeCompletedListener() {
			public void handshakeCompleted(HandshakeCompletedEvent event) {
				System.out.println("Handshake finished!");
				System.out.println("\t CipherSuite:" + event.getCipherSuite());
				System.out.println("\t SessionId " + event.getSession());
				System.out.println("\t PeerHost " + event.getSession().getPeerHost());
			}
		});

		result.startHandshake();

		return result;
	}

	private void doTunnelHandshake(Socket tunnel, String host, int port) throws IOException {
		OutputStream out = tunnel.getOutputStream();
		String msg = "CONNECT " + host + ":" + port + " HTTP/1.0\n" + "User-Agent: "
				+ sun.net.www.protocol.http.HttpURLConnection.userAgent + "\r\n\r\n";
		byte b[];
		try {
			/*
			 * We really do want ASCII7 -- the http protocol doesn't change with
			 * locale.
			 */
			b = msg.getBytes("ASCII7");
		} catch (UnsupportedEncodingException ignored) {
			/*
			 * If ASCII7 isn't there, something serious is wrong, but Paranoia
			 * Is Good (tm)
			 */
			b = msg.getBytes();
		}
		out.write(b);
		out.flush();

		/*
		 * We need to store the reply so we can create a detailed error message
		 * to the user.
		 */
		byte reply[] = new byte[200];
		int replyLen = 0;
		int newlinesSeen = 0;
		boolean headerDone = false; /* Done on first newline */

		InputStream in = tunnel.getInputStream();
		boolean error = false;

		while (newlinesSeen < 2) {
			int i = in.read();
			if (i < 0) {
				throw new IOException("Unexpected EOF from proxy");
			}
			if (i == '\n') {
				headerDone = true;
				++newlinesSeen;
			} else if (i != '\r') {
				newlinesSeen = 0;
				if (!headerDone && replyLen < reply.length) {
					reply[replyLen++] = (byte) i;
				}
			}
		}

		/*
		 * Converting the byte array to a string is slightly wasteful in the
		 * case where the connection was successful, but it's insignificant
		 * compared to the network overhead.
		 */
		String replyStr;
		try {
			replyStr = new String(reply, 0, replyLen, "ASCII7");
		} catch (UnsupportedEncodingException ignored) {
			replyStr = new String(reply, 0, replyLen);
		}

		/*
		 * We check for Connection Established because our proxy returns
		 * HTTP/1.1 instead of 1.0
		 */
		//if (!replyStr.startsWith("HTTP/1.0 200")) {
		System.out.println("reply str : " + replyStr);
		if (replyStr.toLowerCase().indexOf("200 connection established") == -1) {
			throw new IOException("Unable to tunnel through " + tunnelHost + ":" + tunnelPort + ".  Proxy returns \""
					+ replyStr + "\"");
		}

		/* tunneling Handshake was successful! */
	}

	@Override
	public String[] getDefaultCipherSuites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getSupportedCipherSuites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Socket createSocket(String arg0, int arg1) throws IOException, UnknownHostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Socket createSocket(InetAddress arg0, int arg1) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3)
			throws IOException, UnknownHostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2, int arg3) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
