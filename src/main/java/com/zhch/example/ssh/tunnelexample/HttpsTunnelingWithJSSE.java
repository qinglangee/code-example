package com.zhch.example.ssh.tunnelexample;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * Java Tip 111: Implement HTTPS tunneling with JSSE<br>
 * http://www.javaworld.com/article/2077475/core-java/java-tip-111--implement-
 * https-tunneling-with-jsse.html
 * 
 * @author zhch
 *
 */
public class HttpsTunnelingWithJSSE {
	String tunnelHost = "127.0.0.1";
	int tunnelPort = 80;
	String host = "passport.zhaopin.com";
	int port = 443;
	//	String host = "120.24.0.162";
	//	int port = 80;
	//	String tunnelHost = "passport.zhaopin.com";
	//	int tunnelPort = 443;

	String target = "http://www.verisign.com/index.html";

	public static void main(String[] args) throws UnknownHostException, IOException {
		System.setProperty("javax.net.ssl.trustStore",
				"d:\\document\\workspace_luna\\resume_import_system\\jssecacerts");
		HttpsTunnelingWithJSSE t = new HttpsTunnelingWithJSSE();
		t.main();
	}

	public void main() throws UnknownHostException, IOException {
		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

		/*
		 * Set up a socket to do tunneling through the proxy. Start it off as a
		 * regular socket, then layer SSL over the top of it.
		 */
		//		tunnelHost = System.getProperty("https.proxyHost");
		//		tunnelPort = Integer.getInteger("https.proxyPort").intValue();

		Socket tunnel = new Socket(tunnelHost, tunnelPort);
		System.out.println("doTunnelHandshake start 001");
		doTunnelHandshake(tunnel, host, port);
		System.out.println("doTunnelHandshake end 002");

		/*
		 * Ok, let's overlay the tunnel socket with SSL.
		 */
		SSLSocket socket = (SSLSocket) factory.createSocket(tunnel, host, port, true);

		/*
		 * register a callback for handshaking completion event
		 */
		socket.addHandshakeCompletedListener(new HandshakeCompletedListener() {
			public void handshakeCompleted(HandshakeCompletedEvent event) {
				System.out.println("Handshake finished!");
				System.out.println("\t CipherSuite:" + event.getCipherSuite());
				System.out.println("\t SessionId " + event.getSession());
				System.out.println("\t PeerHost " + event.getSession().getPeerHost());
			}
		});

		/*
		 * send http request
		 *
		 * See SSLSocketClient.java for more information about why there is a
		 * forced handshake here when using PrintWriters.
		 */
		socket.startHandshake();

		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

		out.println("GET " + "https://pan.baidu.com/" + " HTTP/1.0");
		out.println();
		out.flush();
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
}
