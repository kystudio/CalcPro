package com.lt.relacalc;

import java.util.Stack;

public class Calc {

	private Stack<String> stack;
	public Calc() {
		stack = new Stack<>();
		stack.push("我");
	}
	
	public Stack<String> getStack() {
		return stack;
	}

	public void setStack(Stack<String> stack) {
		this.stack = stack;
	}

	public void push(String e){
		stack.push(e);
		
	}
	public String getResult(){
		//System.out.println(stack.toString());
		String[] str = stack.toString().split("\\[|\\]|\\,| ");
		StringBuilder sb = new StringBuilder();
		for (String s : str) {
			sb.append(s);
		}
		
		return sb.toString();
	}
	/**
	 * 清空栈
	 */
	public void clear(){
		stack.clear();
	}
	/**
	 * 出栈
	 * @return
	 */
	public String pop(){
		
		if(stack!=null)
		return stack.pop();
		return null;
	}

	public int getStackSize() {
		return stack.size();
	}
	public String get(int index){
		return stack.get(index);
	}
}
