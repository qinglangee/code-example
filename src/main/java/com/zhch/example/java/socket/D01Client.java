package com.zhch.example.java.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class D01Client {
	// 一轮消息，发完就挂
	public static void main(String[] args) {
		D01Client client = new D01Client();
		client.start();
	}

	private void start() {
		try {
			System.out.println("客户端启动。");
            //1.创建socket用来与服务器端进行通信，发送请求建立连接，指定服务器地址和端口
            Socket socket=new Socket("localhost", D01Server.port);
            //2.获取输出流用来向服务器端发送登陆的信息
            OutputStream os=socket.getOutputStream();//字节输出流
//            PrintWriter pw=new PrintWriter(os);//将输出流包装成打印流
            BufferedWriter pw=new BufferedWriter(new OutputStreamWriter(os, "utf-8"));//将输出流包装成打印流
            System.out.println("client 01");
         // 如果服务器是读取行，那客户端一定要写入换行符，否则服务器会一直等换行的
            pw.write("用户名：admin；密码：12345\n");
            pw.flush();
            pw.write("aabb\n");
            pw.flush();
            System.out.println("client 02");
            socket.shutdownOutput();//关闭输出流
            System.out.println("client 03");
            
            //3.获取输入流，用来读取服务器端的响应信息
            InputStream is=socket.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is));//添加缓冲
            String info=null;
            System.out.println("client 04");
            while((info=br.readLine())!=null){
                System.out.println("我是客户端，服务器端说"+info);
            }
            System.out.println("client 05");
 
 
            //4.关闭其他相关资源
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
            System.out.println("client 06");
 
 
 
        } catch (IOException e) {
        	e.printStackTrace();
            System.out.println("我是客户端，连接出错。");
 
        } 
//		catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	
	
}
