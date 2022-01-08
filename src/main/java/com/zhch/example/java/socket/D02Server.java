package com.zhch.example.java.socket;
// https://blog.csdn.net/w_linux/article/details/79394150
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class D02Server {
	
	public static int port = 62224;

	// 本例没有客户端，只有服务器
	// 使用 telnet 模拟客户端  telnet localhost port
	public static void main(String[] args) {
		new GetSocket().start();
		while(true) {
			// 如果有其它需要 server 处理的交互之类的，没有就不必要再循环了。 
		}
	}
}

/*
 *服务器端的侦听类，负责获取连接成功的客户端
 *获取socket与客户端通信 
 *
 */

class GetSocket extends Thread {
    @Override
    public void run() {

        try {
            //创建绑定到特定端口的服务器套接字  1-65535
            ServerSocket serversocket = new ServerSocket(62224);
            System.out.println("服务启动。");
            while(true) {
                //建立连接，获取socket对象
                Socket socket=serversocket.accept();
                //消息提示框
//                JOptionPane.showMessageDialog(null, "有客户端连接到了本机62224端口哦");
                System.out.println("有客户端连接到了本机62224端口哦");
                //与客户端通信
                ChatSocket cs=new ChatSocket(socket);
                cs.start();
                //添加socket到队列
                ChatManager.GetChatManager().AddChatPeople(cs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}


/*
 *与客户端通信，向客户端发送信息 
 *实现单独线程的通信处理
 */
class ChatSocket extends Thread {

    Socket socket;
    public ChatSocket(Socket socket) {
        this.socket=socket;
    }

    /*
     *向客户端输出信息 
     */
    public void Out(String str) throws IOException {
        try {
            socket.getOutputStream().write(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } 
//        catch (IOException e) {
//        	System.out.println("out  error.");
//            e.printStackTrace();
//        }
    }


    /*
     *
     * 向客户端发送消息
     */
    @Override
    public void run() {
    	System.out.println("chatsocket run.");
    	BufferedReader br = null;
        try {
        	Out("success\n");
        	
        	System.out.println("server 01.");
        	br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));

            String line=null;
            System.out.println("server 02");
            while((line=br.readLine())!=null)
            {
                System.out.println(line);
                System.out.println("server 03");
                ChatManager.GetChatManager().broadcast(this, line + "\n");
                System.out.println("server 04");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	if(br != null) {
        		// 读取流必须要关闭，如果因为Exception没有正常关闭，client端就会一直挂起等待，直到超时。  
        		try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	
        }

        System.out.println("chatsocket run over.");
    }
}


/*
 * 
 * 添加socket到队列，发送数据
 * 
 */

class ChatManager {
    //因为一个聊天系统只有一个聊天管理，所以需进行单例化private
    /*
     *单例化 
     */
    private ChatManager() {}
    private static final ChatManager cm=new ChatManager();
    public static ChatManager GetChatManager() {
        return cm;
    }

    //创建保存socket的队列
    Vector<ChatSocket> vector=new Vector<ChatSocket>();

    //添加聊天人
    public void AddChatPeople(ChatSocket cs) {
        vector.add(cs);
    }

    //群发消息
    public void broadcast(ChatSocket cs,String str)  {
        for (int i = 0; i < vector.size(); i++) {
            ChatSocket chatsocket=(ChatSocket)vector.get(i);
            if(!cs.equals(chatsocket))
            {
                	try {
						chatsocket.Out(str);
					} catch (IOException e) {
						System.out.println("ERROR: " + e.getMessage());
			            System.out.println("删除一个client.");
			            ChatManager.GetChatManager().remove(chatsocket);
					}
            }
        }
    }
    
    // 删除连接
    public void remove(ChatSocket cs) {
    	vector.remove(cs);
    }

}