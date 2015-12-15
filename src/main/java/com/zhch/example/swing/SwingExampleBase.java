package com.zhch.example.swing;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class SwingExampleBase extends JFrame {
	private static final long serialVersionUID = 4645560887609012941L;

	public SwingExampleBase() {
		exampleBaseInit();
	}

	public void exampleBaseInit() {
		setTitle("SwingExampleBase");
		setSize(800, 600);
		//		setLocationByPlatform(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SwingExampleBase();
			}
		});
	}
}
