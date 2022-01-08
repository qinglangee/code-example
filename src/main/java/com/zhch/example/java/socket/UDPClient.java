import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) throws Exception
    {
        DatagramSocket ds=null;
        DatagramPacket dpSend=null;
        InetAddress ia=InetAddress.getByName("127.0.0.1");
        int port=3021;

        try
        {
            ds=new DatagramSocket();
            for(int i=0;i<5;i++)
            {
                byte[] data=("我是 UDP 客户端"+i).getBytes();
                dpSend=new DatagramPacket(data,data.length,ia,port);
                ds.send(dpSend);
                Thread.sleep(1000);
            }
            ds.close();
        }
        catch(IOException | InterruptedException e)
        {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }   
    }
}
