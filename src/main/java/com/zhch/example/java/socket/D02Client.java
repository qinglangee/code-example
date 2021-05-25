package com.zhch.example.java.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
/**
 * 这是一个能收能发的客户端，可以用来跟连接服务器的telnet通信
 * @author zhch
 *
 */
public class D02Client {
	
	public String msg;
	public boolean running = true;
	public Socket socket = null;
	OutputStream os= null;
	PrintWriter pw=null;
	
	public static void main(String[] args) {
		D02Client client = new D02Client();
		client.start();
	}

	public void sendMsg(String msg) {
		pw=new PrintWriter(os);//将输出流包装成打印流
		pw.write(msg + "\n");
		pw.flush();
		
	}
	private void start() {
		try {
			System.out.println("客户端启动。");
            //1.创建socket用来与服务器端进行通信，发送请求建立连接，指定服务器地址和端口
            socket=new Socket("localhost", D02Server.port);
            os=socket.getOutputStream();//字节输出流
            
            D02ClientThread client = new D02ClientThread(socket);
            client.start();
            
            Scanner sc = new Scanner(System.in);
            sendMsg("ha lou");
            while(running) {
            	String a = sc.next();
            	System.out.println("a is :" + a);
            	sendMsg(a);
            }
            
            System.out.println("close socket.");
            pw.close();
            os.close();
            socket.close();
 
 
 
        } catch (IOException e) {
        	e.printStackTrace();
            System.out.println("我是客户端，连接出错。");
 
        } 
	}
	class D02ClientThread extends Thread{
		Socket socket;
		public D02ClientThread(Socket socket) {
			this.socket = socket;
		}
		public void run() {
			int num = 1;
			OutputStream os= null;
			PrintWriter pw=null;
			InputStream is=null;
			BufferedReader br=null;
				
			try {
				while(running && num < 5) {
					System.out.println("client run 01");
					
					//3.获取输入流，用来读取服务器端的响应信息
					is=socket.getInputStream();
					br=new BufferedReader(new InputStreamReader(is));//添加缓冲
					
					String info=null;
					System.out.println("client 01");
					while((info=br.readLine())!=null){
						System.out.println("我是客户端，服务器端说"+info);
					}
					System.out.println("client 02");
					
					br.close();
					is.close();
					
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}

