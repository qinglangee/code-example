/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package com.zhch.example.commons.http.v4_5;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * How to send a request via proxy.<br>
 * 官方proxy示例<br>
 * 蚂蚁代理 http://www.mayidaili.com/
 *
 * @since 4.0
 */
public class ClientExecuteProxy {


	public static void proxyExample() throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpHost proxy = new HttpHost("24.157.37.61", 8080, "http");

			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
			HttpGet request = new HttpGet("http://www.ip.cn");
			request.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1");

			request.setConfig(config);

			System.out.println(
					"Executing request " + request.getRequestLine() + " to " + request.getURI() + " via " + proxy);

			CloseableHttpResponse response = httpclient.execute(request);
			try {
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				System.out.println(EntityUtils.toString(response.getEntity(), "utf8"));
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

	public static void main(String[] args) throws Exception {
		proxyExample();
	}

}
