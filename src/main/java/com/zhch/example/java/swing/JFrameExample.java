package com.zhch.example.java.swing;

import javax.swing.JFrame;

/**
 * Example for JFrame JButton JText
 * 
 * @author zhch
 *
 */
public class JFrameExample extends JFrame{
	private static final long serialVersionUID = -117601958390892637L;

	public void init() {
		setTitle("JFrame example");
		setSize(600, 500);

		setLocationByPlatform(true);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		JFrameExample t = new JFrameExample();
		t.init();
	}

}
