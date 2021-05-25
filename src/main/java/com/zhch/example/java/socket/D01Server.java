package com.zhch.example.java.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

public class D01Server {
	public static int port = 10888;

	private void start() {
		try {
            //创建绑定到特定端口的服务器套接字  1-65535
            ServerSocket serversocket = new ServerSocket(port);
            System.out.println("服务启动。");
            while(true) {
                //建立连接，获取socket对象
                Socket socket=serversocket.accept();
                System.out.println("有客户端连接到了本机端口哦");
                
                // 读取输入
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));

               String line=null;
            // 如果服务器是读取行，那客户端一定要写入换行符，否则服务器会一直等换行的
               while((line=br.readLine())!=null)
               {
                   System.out.println(line);
               }
                   
                //与客户端通信
                // socket 读与写不能同时进行，想写时客户端已经关闭了。  
                String str = "hello client.";
                socket.getOutputStream().write(str.getBytes("UTF-8"));
                
                br.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		D01Server server = new D01Server();
		server.start();
	}
}
