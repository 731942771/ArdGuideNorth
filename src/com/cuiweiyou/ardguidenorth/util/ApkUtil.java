package com.cuiweiyou.ardguidenorth.util;

import com.cuiweiyou.ardguidenorth.app.RootApplication;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * <b>类名</b>: ApkUtil.java，apk文件工具类 <br/>
 * <b>说明</b>: <br/>
 * &emsp;&emsp; getVersionName()：获取版本<br/>
 * <b>创建</b>: 2016-2016年6月22日_上午10:22:27 <br/>
 * 
 * @author cuiweiyou.com <br/>
 * @version  <br/>
 */
public class ApkUtil {
	
	private static PackageManager packageManager;

	private ApkUtil(){};
	
	/**
	 * <b>功能</b>：getVersionCode，获取app的版本号 <br/>
	 * <b>说明</b>: 使用全局appctx的静态方法拿到ctx。<br/>
	 * &emsp;&emsp; 如果获取错误，返回 0<br/>
	 * <b>创建</b>：2016年6月22日上午10:09:40<br/>
	 * 
	 * @return int 版本号
	 * @author cuiweiyou.com
	 */
	public static int getVersionCode() {
		if(null == packageManager)
			packageManager = RootApplication.getAppContext().getPackageManager();

		try {
			// 2.应用包信息，获取AndroidManifest.xml中的配置信息
			PackageInfo packageInfo = packageManager.getPackageInfo(RootApplication.getAppContext().getPackageName(), 0);

			// 3.版本名
			// String versionName = packageInfo.versionName;
			int versionCode = packageInfo.versionCode;

			// 4.return
			return versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return 0;
	}
	/**
	 * <b>功能</b>：getVersionName，获取app的版本名称 <br/>
	 * <b>说明</b>: 使用全局appctx的静态方法拿到ctx。<br/>
	 * &emsp;&emsp; 如果获取错误，返回“error”<br/>
	 * <b>创建</b>：2016年6月22日上午10:09:40<br/>
	 * 
	 * @return int 版本号
	 * @author cuiweiyou.com
	 */
	public static String getVersionName() {
		if(null == packageManager)
			packageManager = RootApplication.getAppContext().getPackageManager();
		
		try {
			// 2.应用包信息，获取AndroidManifest.xml中的配置信息
			PackageInfo packageInfo = packageManager.getPackageInfo(RootApplication.getAppContext().getPackageName(), 0);
			
			// 3.版本名
			 String versionName = packageInfo.versionName;
			
			// 4.return
			return versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		return "error";
	}

}
