package com.zhch.example.ssh.tunnelexample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLTunnelReader {
	private final static String proxyHost = "120.24.0.162";
	private final static String proxyPort = "80";

	public static void main(String[] args) throws Exception {
		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
		//System.setProperty("https.proxyHost",proxyHost);
		//System.setProperty("https.proxyPort",proxyPort);

		URL verisign = new URL("https://pan.baidu.com");
		URLConnection urlc = verisign.openConnection(); //from secure site
		if (urlc instanceof com.sun.net.ssl.HttpsURLConnection) {
			((com.sun.net.ssl.HttpsURLConnection) urlc)
					.setSSLSocketFactory(new SSLTunnelSocketFactory(proxyHost, proxyPort));
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));

		String inputLine;

		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);

		in.close();
	}
}
