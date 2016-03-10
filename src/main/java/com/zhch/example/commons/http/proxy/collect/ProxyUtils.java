package com.zhch.example.commons.http.proxy.collect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ProxyUtils {

	static String host = "passport.zhaopin.com";
	static int port = 443;

	public static boolean canTunnel(String tunnelHost, int tunnelPort) {
		try {
			Socket tunnel = new Socket(tunnelHost, tunnelPort);
			//			System.out.println("doTunnelHandshake start 001 - " + tunnelHost + ":" + tunnelPort);
			doTunnelHandshake(tunnel, host, port);
			//			System.out.println("doTunnelHandshake end 002");
			return true;
		} catch (IOException e) {
			//			e.printStackTrace();
			System.out.println("connect error!!!");
		}
		return false;
	}

	private static void doTunnelHandshake(Socket tunnel, String host, int port) throws IOException {
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
		//		System.out.println("reply str : " + replyStr);
		if (replyStr.toLowerCase().indexOf("200 connection established") == -1) {
			throw new IOException("Unable to tunnel through " + tunnel.getInetAddress().getHostAddress() + ":"
					+ tunnel.getPort() + ".  Proxy returns \"" + replyStr + "\"");
		}

		/* tunneling Handshake was successful! */
	}


	public static void main(String[] args) {
		String host = "114.215.104.175";
		int port = 8080;

		ProxyUtils t = new ProxyUtils();
		boolean can = t.canTunnel(host, port);
		System.out.println(host + ":" + port + " can tunnel is :" + can);
	}
}
