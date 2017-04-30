package com.lt.calcpro;

import com.lt.bigcalc.BigCalcFrame;
import com.lt.bigcalc.CalcModel;
import com.lt.bigcalc.Controller;

public class App {

	public static void main(String[] args) {
		// 模型
		
		CalcModel model = new CalcModel();
		// 控制器
		Controller controller = new Controller();
		controller.setModel(model);
		// 视图
		BigCalcFrame panel1 = new BigCalcFrame(controller);
	
		// 模型设置回调接口（视图实现了回调接口）
		model.setCallback(panel1);
		controller.setCallback(panel1);
		model.addObserver(panel1);
		
	}
}
