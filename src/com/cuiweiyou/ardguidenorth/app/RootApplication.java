package com.cuiweiyou.ardguidenorth.app;

import android.app.Application;
import android.content.Context;

/**
 * <b>类名</b>: RootApplication.java，全局的Application对象 <br/>
 * <b>说明</b>: <br/>
 * &emsp;&emsp; 提供getAppContext()方法获取全局ctx <br/>
 * <b>创建</b>: 2016-2016年6月22日_上午10:48:31 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class RootApplication extends Application{
	
	public static RootApplication mRootApp;

	@Override
	public void onCreate() {
		super.onCreate();
		
		mRootApp = this;
	}
	
	/**
	 * <b>功能</b>：getAppContext，获取全局ctx <br/>
	 * <b>创建</b>：2016年6月22日上午10:05:59<br/>
	 * 
	 * @return Context 全局ctx
	 * @author cuiweiyou.com
	 */
	public static Context getAppContext() {
		return mRootApp;
	}
}
