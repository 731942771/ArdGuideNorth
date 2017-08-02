package com.cuiweiyou.ardguidenorth.tool;

import com.cuiweiyou.ardguidenorth.app.RootApplication;

import android.util.Log;
import android.widget.Toast;

/**
 * <b>类名</b>: JavaScriptConnectToJava.java，WebView的JS通讯接口 <br/>
 * <b>说明</b>: <br/>
 * <b>创建</b>: 2016-2016年6月22日_下午9:32:01 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class JavaScriptConnectToJava {

	/**
	 * <b>功能</b>：showSource，查看页面源码 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月22日下午9:32:22<br/>
	 * 
	 * @param html
	 */
	public void showSource(String html) {
		Log.e("ard", "页面源码：" + html);
		
		Toast.makeText(RootApplication.getAppContext(), "登陆成功", 0).show();
	}
}
