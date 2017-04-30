package com.lt.util;

/**
 * 回调接口
 * @author litong
 *
 */
public interface CalcCallback {

	/**
	 * 显示按钮的输入
	 */
	void showInput(String str);
	/**
	 * 显示结果
	 */
	void showResult(String str);
	/**
	 * 异常
	 */
	void error(int i);
	/**
	 * 恢复
	 */
	void reset();
	/**
	 * 切换界面
	 */
	void chageFrame();
}
