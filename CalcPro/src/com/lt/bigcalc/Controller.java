package com.lt.bigcalc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.lt.relacalc.ButtonListener;
import com.lt.relacalc.RelaCalcFrame;
import com.lt.util.CalcCallback;
import com.lt.util.Util;

public class Controller extends Observable implements ActionListener {

	/**
	 * 计算器的核心
	 */
	private CalcModel calcModel;
	private static final String ERROR_ZERO = "DividedZero";
	private StringBuilder input = new StringBuilder();
	private CalcCallback calcCallback;

	/** 支持的运算符 **/
	private static String[] operator = { "+", "-", "*", "/" };
	/** 运算符优先级 **/
	private static Map<String, Integer> priority = new HashMap<>();
	static {
		priority.put("*", 2);
		priority.put("/", 2);
		priority.put("+", 1);
		priority.put("-", 1);
		priority.put("", -1); // 无运算符
	}

	public void setModel(CalcModel model) {
		this.calcModel = model;
	}
	public CalcModel getModel(){
		return calcModel;
	}

	public void setCallback(BigCalcFrame view) {
		this.calcCallback = view;
	}

	public Controller() {
		super();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		String pop = "";
		switch (command) {

		case "√":
			calcModel.push(command);
			break;
		case "+":
		case "-":
		case "*":
		case "/":
			calcModel.push(command);
			break;
		case "=":
			if(!calcModel.getResult().contains("√")){
			String r = suffixToValue(inffixToSuffix(calcModel.getResult()));
			//System.out.println("r: "+r);
			calcModel.push(r);
			}else{
				String temp = calcModel.getResult().substring(0,calcModel.getResult().length()-1);
				calcModel.clear();
				calcModel.push(Util.sqrt(temp));
			}
			
			break;
		case "C":
			calcModel.clear();
			calcModel.push("");

			break;
		case "Del":
			if(calcModel.getStackSize()>0){
			calcModel.pop();
			System.out.println(calcModel.getResult());
			}
			break;
		case "Rela":
			RelaCalcFrame view = new RelaCalcFrame ();
			ButtonListener listenner = new ButtonListener(view);
			calcCallback.chageFrame();
			break;
		default:
			input.append(command);
			calcModel.push(input.toString());
			input.delete(0, input.length());			
			break;

		}

	}

	/**
	 * 根据后缀表达式算出结果
	 * 从左到右遍历后缀表达式 遇到数字就进栈 遇到符号，就将栈顶的两个数字出栈运算，运算结果进栈，直到获得最终结果。
	 * 
	 * @param expression
	 *            后缀表达式
	 * @return 结果
	 */
	private String suffixToValue(String expression) {

		// 已经用空格分隔，直接分割
		String[] suffix = expression.split(" ");
		System.out.println(expression);
		calcModel.clear();
		String num1 = "", num2 = ""; 
		String tmp = "";

		for (int i = 0; i < suffix.length; i++) {

			if (isNum(suffix[i])) { // 数字
				calcModel.push(suffix[i]);
			} else { // 是操作符
				num2 = calcModel.pop();
				num1 = calcModel.pop();
				System.out.println(num1 + " " + num2);
				tmp = calculate(num1, num2, suffix[i]);

				if (ERROR_ZERO.equals(tmp)) {
					calcModel.push("请重新输入： ");
					calcCallback.error(3);
				} else {
					calcModel.push(tmp);
				}
			}
		}
       
		// 最终结果也压在栈中，取出即可
		return calcModel.popx();
	}

	/**
	 * 运算部分
	 * 
	 * @param num1
	 *            第一个数，在前
	 * @param num2
	 *            第二个数，在后
	 * @param operator
	 *            运算符
	 * @return 运算结果，除数为0则返回Error
	 */
	private static String calculate(String num1, String num2, String operator) {
		String result = "";

		switch (operator) {
		case "+":
			result = Util.sum(num1, num2);
			break;
		case "-":
			result = Util.minus(num1, num2);
			break;
		case "*":

			result = Util.multiply(num1, num2);
			break;
		case "/":
			 if (num2 .equals("0")) { // 除数为0
			 return ERROR_ZERO;
			 }
			 result = Util.divided(num1, num2);
			break;
		case "√":
			result = Util.sqrt(num1);
			break;
		default:
			break;
		}
		
		return result;
	}

	/**
	 * 将中缀表达式转换成后缀表达式 ：
	 * +，元素之间之间用空格分隔。 从左到右遍历中缀表达式的每一个数字和运算符 如果数字就输出（即存入后缀表达式）
	 * 如果优先级低于栈顶运算符，则弹出栈顶运算符，并将当前运算符进栈 遍历结束后，将栈则剩余运算符弹出。
	 * 
	 * @param expression
	 *            中缀表达式
	 * @return 后缀表达式
	 */
	private String inffixToSuffix(String expression) {
		calcModel.clear();
		StringBuilder inffix = new StringBuilder(expression);
		StringBuilder suffix = new StringBuilder();
		String element = ""; // 中缀表达式的数字或者运算符
		String tmp = "";
		while (inffix.length() > 0) {
			element = popNextElement(inffix);
			System.out.println("element: "+element);
			if (isNum(element)) { // 是数字则输出
				suffix.append(element).append(" ");
				System.out.println("aaa"+suffix.toString());
			}  else if ( priority.get(element) >= priority.get(getTopOperator())) {
				//System.out.println("element: "+element);
				calcModel.push(element);
			} else { // 优先级小于栈顶运算符，则弹出
				tmp = calcModel.pop();
				calcModel.push(element);
				System.out.println("tmp:"+tmp);
				//suffix.append(tmp).append(" ").append(element).append(" ");
				suffix.append(tmp).append(" ");
			}
		}

		// 把栈中剩余运算符都弹出
		while (calcModel.getStackSize() > 0) {
			System.out.println(111);
			suffix.append(calcModel.pop()).append(" ");
		}
		return suffix.toString();
	}

	/**
	 * 获取栈顶运算符
	 * 
	 * @return 如果栈中无运算符，则返回空字符串
	 */
	private String getTopOperator() {
		String tmp = "";
		for (int i = calcModel.getStackSize() - 1; i >= 0; i--) {
			tmp = calcModel.get(i);
			if ("(".equals(tmp)) {
				break;
			} else if (isOperator(tmp)) {
				return tmp;
			}
		}
		return "";
	}

	/**
	 * 检测一个字符是不是运算符 栈中不是运算符就是括号
	 * 
	 * @param str
	 *            待检测字符，由于可能有多位数字字符串，这里用的是String
	 * @return 检测结果
	 */
	private static boolean isOperator(String str) {
		for (int i = 0; i < operator.length; i++) {
			if (operator[i].equals(str)) {
				return true;
			}
		}
		return false;
	}
    /**
     * 检测字符串是否是数字
     * 把.和10种数字放进list里，如果list里包含字符串中的字符，并且包含次数等于字符串的长度该字符串就是数字
     * @param str
     * @return
     */
	private boolean isNum(String str) {
		ArrayList<Character> list = new ArrayList<>();
		
		list.add('.');
		for (int i = 0; i < 10; i++) {
			String s = String.valueOf(i);
			list.add(s.charAt(0));
		}		
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if(list.contains(str.charAt(i))){
				count++;
				//System.out.println(count);
			}else{
				break;				
			}
			if(count==str.length()){
				return true;
			}
		}
		return false;
	}
    /**
     * 取出下一个
     * @param expression
     * @return
     */
	private String popNextElement(StringBuilder expression) {
		StringBuilder result = new StringBuilder();
		char c = expression.charAt(0);
		expression.deleteCharAt(0);
		result.append(c);

		if (isNum(c)) {
			// 如果第一次取到的是数字，则继续检查
			while (expression.length() > 0 && isNum(c = expression.charAt(0))) {
				expression.deleteCharAt(0);
				result.append(c);
			}
		}
		return result.toString();

	}

	private boolean isNum(char c) {
		if ((c >= '0' && c <= '9') || c == '.') {
			return true;
		}
		return false;
	}

}
