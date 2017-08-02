package com.cuiweiyou.ardguidenorth.util;

import com.cuiweiyou.ardguidenorth.app.RootApplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * <b>类名</b>: IntentStateUtil.java，网络状态判断 <br/>
 * <b>说明</b>: 
 * <li>权限 &lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/&gt;
 * <li>getWifiState()，获取wifi状态 
 * <br/>
 * <br/>
 * <b>创建</b>: 2016-2016年6月22日_上午11:06:48 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class IntentStateUtil {
	
	private static ConnectivityManager manager;

	private IntentStateUtil(){}

	/**
	 * <b>功能</b>：getWifiState，获取wifi状态 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月22日上午11:07:46<br/>
	 * 
	 * @return true wifi可用<br/>
	 */
	public static boolean getWifiState(){
		if(null == manager)
			manager = (ConnectivityManager) RootApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		
		// Wifi网络信息对象
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(null == networkInfo)
			return false;
		
		if(networkInfo.isConnected())
			return true;
		
		return false;
	}

}
