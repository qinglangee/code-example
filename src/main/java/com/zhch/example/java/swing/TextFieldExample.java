package com.zhch.example.java.swing;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class TextFieldExample extends SwingExampleBase {
	private static final long serialVersionUID = 8715193323746796755L;

	public TextFieldExample() {
		setTitle("TextFieldExample");
		addComponents();
		// validate();
	}

	private void addComponents() {
		JTextField first = new JTextField("设置文字颜色");
		first.setForeground(Color.RED); // 设置文字颜色
		add(BorderLayout.NORTH, first);

		// validate() 方法放在构造函数里时是不起作用的，why?? 
		validate();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TextFieldExample();
			}
		});
	}
}
