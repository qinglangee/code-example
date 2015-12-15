package com.zhch.example.swing;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 * A Visual Guide to Layout Managers<BR>
 * http://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
 * 
 * @author zhch
 *
 */
public class LayoutExample extends JFrame {
	public void init() {
		setTitle("Layout Example");

		JTabbedPane tabbedPane = new JTabbedPane();

		Container panel0 = new Container();
		demoBorderLayout(panel0);
		tabbedPane.addTab("Border", null, panel0, "BorderLayout");

		Container panel1 = new Container();
		demoFlowLayout(panel1);
		tabbedPane.addTab("Flow", null, panel1, "FlowLayout");

		Container panel2 = new Container();
		demoBoxLayout(panel2);
		tabbedPane.addTab("Box", null, panel2, "BoxLayout");

		Container panel3 = new Container();
		demoCardLayout(panel3);
		tabbedPane.addTab("Card", null, panel3, "CardLayout");

		Container panel4 = new Container();
		demoGridLayout(panel4);
		tabbedPane.addTab("Grid", null, panel4, "GridLayout");

		Container panel5 = new Container();
		demoGridBagLayout(panel5);
		tabbedPane.addTab("GridBag", null, panel5, "GridBagLayout");

		tabbedPane.setSelectedIndex(5);

		//将选项卡添加到panl中
		add(tabbedPane);

		setSize(800, 600);
		setLocationByPlatform(true);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * http://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
	 * 
	 * @param panel5
	 */
	private void demoGridBagLayout(Container pane) {
		boolean shouldFill = true;
		boolean shouldWeightX = true;
		JButton button;
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		if (shouldFill) {
			//natural height, maximum width
			c.fill = GridBagConstraints.HORIZONTAL;
		}

		button = new JButton("Button 1");
		if (shouldWeightX) {
			c.weightx = 0.5;
		}
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(button, c);

		button = new JButton("Button 2");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		pane.add(button, c);

		button = new JButton("Button 3");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		pane.add(button, c);

		button = new JButton("Long-Named Button 4");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40; //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(button, c);

		button = new JButton("5");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0; //reset to default
		c.weighty = 1.0; //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10, 0, 0, 0); //top padding
		c.gridx = 1; //aligned with button 2
		c.gridwidth = 2; //2 columns wide
		c.gridy = 2; //third row
		pane.add(button, c);

	}

	/**
	 * 相同的格子显示组件 http://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html
	 * 
	 * @param panel4
	 */
	private void demoGridLayout(Container c) {
		c.setLayout(new GridLayout(5, 3));

		for (int i = 0; i < 8; i++) {
			c.add(new JButton("grid layout button " + i));
		}

		for (int i = 0; i < 5; i++) {
			c.add(new JTextField("text field " + i));
		}

	}

	/**
	 * 相当于 tabbed pane 的基础模式
	 * http://docs.oracle.com/javase/tutorial/uiswing/layout/card.html
	 * 
	 * @param cards
	 */
	private void demoCardLayout(Container c) {
		c.setLayout(new BorderLayout());

		Container cards = new Container();
		cards.setLayout(new CardLayout());

		String BUTTONPANEL = "Card with JButtons";
		String TEXTPANEL = "Card with JTextField";

		//Where the components controlled by the CardLayout are initialized:
		//Create the "cards".
		JPanel card1 = new JPanel();
		card1.add(new JButton("button"));
		JPanel card2 = new JPanel();
		card2.add(new JTextField("text field"));

		//Create the panel that contains the "cards".
		cards.add(card1, "Card with JButtons");
		cards.add(card2, "Card with JTextField");

		//Where the GUI is assembled:
		//Put the JComboBox in a JPanel to get a nicer look.
		JPanel comboBoxPane = new JPanel(); //use FlowLayout
		String comboBoxItems[] = { BUTTONPANEL, TEXTPANEL };
		JComboBox<String> cb = new JComboBox<>(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, (String) e.getItem());
			}
		});
		comboBoxPane.add(cb);

		c.add(comboBoxPane, BorderLayout.PAGE_START);
		c.add(cards, BorderLayout.CENTER);

	}

	/**
	 * boxLayout 相当于只有一行或一列的 FlowLayout
	 * http://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
	 * 
	 * @param c
	 */
	private void demoBoxLayout(Container c) {
		c.setLayout(new BoxLayout(c, BoxLayout.LINE_AXIS));
		for (int i = 0; i < 8; i++) {
			c.add(new JButton("button " + i));
		}
		JButton change = new JButton("now is LINE_AXIS");
		c.add(change);

		// 切换排列方式
		change.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BoxLayout lay = (BoxLayout) c.getLayout();
				if (lay.getAxis() == BoxLayout.PAGE_AXIS) {
					c.setLayout(new BoxLayout(c, BoxLayout.LINE_AXIS));
				} else {
					c.setLayout(new BoxLayout(c, BoxLayout.PAGE_AXIS));
				}
				c.validate();
			}
		});

	}

	/**
	 * How to Use FlowLayout.<br>
	 * http://docs.oracle.com/javase/tutorial/uiswing/layout/flow.html
	 * 
	 * @param c
	 */
	private void demoFlowLayout(Container c) {
		c.setLayout(new FlowLayout());
		for (int i = 0; i < 8; i++) {
			c.add(new JButton("i:" + i));
		}
		for (int i = 0; i < 5; i++) {
			c.add(new JButton("which button has a very long name " + i));
		}
	}

	/**
	 * How to Use BorderLayout.<br>
	 * http://docs.oracle.com/javase/tutorial/uiswing/layout/border.html)
	 * 
	 * @param c
	 * @return
	 */
	private void demoBorderLayout(Container c) {
		c.setLayout(new BorderLayout());
		c.add(BorderLayout.PAGE_START, new JButton("north"));
		c.add(BorderLayout.PAGE_END, new JButton("sout"));
		c.add(BorderLayout.CENTER, new JButton("center"));
		c.add(BorderLayout.LINE_START, new JButton("east"));
		c.add(BorderLayout.LINE_END, new JButton("west"));
	}

	public static void main(String[] args) {
		LayoutExample t = new LayoutExample();
		t.init();
	}
}
