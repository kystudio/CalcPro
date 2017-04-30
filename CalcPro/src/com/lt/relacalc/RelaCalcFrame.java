package com.lt.relacalc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.lt.util.CalcCallback;

public class RelaCalcFrame extends JFrame implements CalcCallback {

	// 第一行
	private JTextField showText;
	// 第二行
	private JTextField resultText;

	String[] texts = { "夫", "妻", "←", "AC", "父", "母", "兄", "弟", "姐", "妹", "子", "女", "Big","互查", "=" };
	String[] means = { "老公", "媳妇", "老爸", "老妈", "哥哥", "弟弟", "姐姐", "妹妹", "儿子", "女儿" };
	private JButton[] buttons = new JButton[texts.length];
	ButtonListener listener;

	public RelaCalcFrame() {
		listener = new ButtonListener(this);
		initUi();
		setVisible(true);
	}

	private void initUi() {
		setTitle("三姑六婆计算器");
		setSize(320, 480);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 显示面板
		JPanel showPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		add(showPanel, BorderLayout.NORTH);

		showText = new JTextField("");
		showText.setBorder(new EmptyBorder(20, 20, 25, 20));
		showText.setHorizontalAlignment(SwingConstants.RIGHT);
		showText.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		showText.setEditable(false);
		showText.setBackground(new Color(251, 240, 176));
		showPanel.add(showText);

		resultText = new JTextField("我");
		resultText.setBorder(new EmptyBorder(20, 20, 25, 20));
		resultText.setHorizontalAlignment(SwingConstants.RIGHT);
		resultText.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		resultText.setEditable(false);
		resultText.setBackground(new Color(251, 240, 176));
		showPanel.add(resultText);

		JPanel buttonPanel = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		buttonPanel.setLayout(gb);
		GridBagConstraints gbc = new GridBagConstraints();
		add(buttonPanel, BorderLayout.CENTER);
		gbc.fill = GridBagConstraints.BOTH;
		for (int i = 0; i < texts.length; i++) {
			buttons[i] = new JButton(texts[i]);
			buttons[i].setOpaque(true);
			String s = getRandColor();
			buttons[i].setBackground(new Color(Integer.parseInt(s.substring(0, 2), 16),
					Integer.parseInt(s.substring(2, 4), 16), Integer.parseInt(s.substring(4), 16)));
			buttons[i].setForeground(Color.WHITE);
			buttons[i].setFont(new Font("微软雅黑", Font.PLAIN, 16));
			buttons[i].addActionListener(listener);
			buttonPanel.add(buttons[i]);

		}
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[0], gbc);
		buttons[0].setActionCommand("的" + means[0]);

		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[1], gbc);
		buttons[1].setActionCommand("的" + means[1]);

		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[2], gbc);
		buttons[2].setActionCommand("");

		gbc.gridwidth = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[3], gbc);

		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[4], gbc);
		buttons[4].setActionCommand("的" + means[2]);

		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[5], gbc);
		buttons[5].setActionCommand("的" + means[3]);

		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[6], gbc);
		buttons[6].setActionCommand("的" + means[4]);

		gbc.gridwidth = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[7], gbc);
		buttons[7].setActionCommand("的" + means[5]);

		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[8], gbc);
		buttons[8].setActionCommand("的" + means[6]);

		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[9], gbc);
		buttons[9].setActionCommand("的" + means[7]);

		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[10], gbc);
		buttons[10].setActionCommand("的" + means[8]);

		gbc.gridwidth = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[11], gbc);
		buttons[11].setActionCommand("的" + means[9]);

		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[12], gbc);

		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[13], gbc);
		buttons[13].setEnabled(false);
		
		gbc.gridwidth = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(buttons[14], gbc);

	}

	public static String getRandColor() {
		String r, g, b;
		Random random = new Random();
		r = Integer.toHexString(random.nextInt(256)).toUpperCase();
		g = Integer.toHexString(random.nextInt(256)).toUpperCase();
		b = Integer.toHexString(random.nextInt(256)).toUpperCase();
		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;
		return r + g + b;
	}

	@Override
	public void showInput(String str) {
		// 如果字符串大于一定长度就缩小字体
		if (str.length() <= 8) {
			showText.setFont(new Font("微软雅黑", Font.PLAIN, 28));
			showText.setText(str);
		} else if (str.length() > 8 && str.length() <= 12) {
			showText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			showText.setText(str);
		} else if (str.length() > 12 && str.length() <= 18) {
			showText.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			showText.setText(str);
		} else if (str.length() > 18) {
			System.out.println(str.length() + " " + str);
			JOptionPane.showMessageDialog(this, "你是要看家谱吗！！！");
		}

	}

	@Override
	public void showResult(String str) {
		// 如果字符串大于一定长度就缩小字体
		if (str.length() <= 8) {
			resultText.setFont(new Font("微软雅黑", Font.PLAIN, 28));
			resultText.setText(str);
		} else if (str.length() > 8 && str.length() <= 12) {
			resultText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			resultText.setText(str);
		} else if (str.length() > 12 && str.length() <= 18) {
			resultText.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			resultText.setText(str);
		} else if (str.length() > 18) {
			System.out.println(str.length() + " " + str);
			JOptionPane.showMessageDialog(this, "你是要看家谱吗！！！");
		}

	}

	@Override
	public void error(int arg) {
		if (arg == 1) {
			for (int i = 0; i < texts.length; i++) {
				if (i != 2 && i != 3 && i != 12 && i != 13&&i !=14) {
					buttons[i].setEnabled(false);
				}
			}
		} else if (arg == 2) {
			JOptionPane.showMessageDialog(this, "不能再后退了好吧0.0");
		}else if(arg ==3){
			buttons[13].setEnabled(true);
			for (int i = 0; i < texts.length; i++) {
				if (i != 2 && i != 3 && i != 12 && i != 13&&i !=14) {
					buttons[i].setEnabled(false);
				}
			}
		}
	}

	/**
	 * 恢复按钮不能点击的状态
	 */
	@Override
	public void reset() {
		for (int i = 0; i < texts.length; i++) {
			if (i != 2 && i != 3 && i != 12 && i != 13&&i !=14) {
				buttons[i].setEnabled(true);
			}
		}
	}

	@Override
	public void chageFrame() {
		this.dispose();
	}
}
