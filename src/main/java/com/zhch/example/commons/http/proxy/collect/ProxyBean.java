package com.zhch.example.commons.http.proxy.collect;

public class ProxyBean {
	String host;
	int port;
	String type;

	public ProxyBean(String host, String port, String type) {
		this(host, Integer.parseInt(port), type);
	}

	public ProxyBean(String host, int port, String type) {
		this.host = host;
		this.port = port;
		this.type = type;
	}

	public String toString() {
		String str = host + ":" + port + " " + type;
		return str;
	}

}
