package com.zhch.example.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

/**
 * Demo from : http://blog.csdn.net/zapldy/article/details/3971579
 * 
 * @author zhch
 *
 */
public class ReceiveMailDemo {
	public Store getStore() throws MessagingException {
		String host = "pop.ym.163.com";
		String username = "fan.fan@souqiantu.com";
		String password = "fytsix";

		// Create empty properties
		Properties props = new Properties();

		// Get session
		Session session = Session.getDefaultInstance(props, null);
		// Get the store
		Store store = session.getStore("pop3");
		store.connect(host, username, password);
		return store;
	}

	// 获取全部 message
	public void getAllMessage(Store store) throws MessagingException {

		// Get folder
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);

		// Get directory
		Message message[] = folder.getMessages();
		for (int i = 0, n = message.length; i < n; i++) {
			if (message[i].getSubject().contains("【初级java开发工程师_北京-海淀区】 刘艳斌_来自猎聘网的候选人")) {
				System.out.println(i + ": " + message[i].getFrom()[0]
				+ "\t" + message[i].getSubject());

				try {
					File file = new File("d:\\temp\\d3\\result.mht");
					//					FileUtils.write(file, message[i].getContent().toString(),
					//							"utf-8");
					//					System.out.println(message[i].getContent());
					OutputStream os = new FileOutputStream(file);
					message[i].writeTo(os);
					message[i].writeTo(System.out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		// Close connection
		folder.close(false);
	}

	public void testMessageIndex(Store store) throws MessagingException {

		// Get folder
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);

		int count = folder.getMessageCount();
		for (int i = count; i > count - 20; i--) {
			Message message = folder.getMessage(i);
			String subject = message.getSubject();
			System.out.println("112  " + subject + "211");

			File file = new File("d:\\temp\\d3\\111\\" + subject + ".mht");
			OutputStream os;
			try {
				os = new FileOutputStream(file);
				message.writeTo(os);
				//				message.writeTo(System.out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		folder.close(false);
	}

	public static void main(String[] args) throws MessagingException {
		ReceiveMailDemo t = new ReceiveMailDemo();
		// 初始化 store
		Store store = t.getStore();

		//		t.getAllMessage(store);
		t.testMessageIndex(store);

		// 关闭 store
		store.close();
		System.out.println("over!!!");
	}
}
