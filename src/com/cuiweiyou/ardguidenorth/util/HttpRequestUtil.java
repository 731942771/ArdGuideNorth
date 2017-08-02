package com.cuiweiyou.ardguidenorth.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Message;

/**
 * <b>类名</b>: HttpRequestUtil.java，网络请求工具类 <br/>
 * <b>说明</b>:
 * &emsp;&emsp; <li>注意注册权限 &lt;uses-permission android:name="android.permission.INTERNET" /&gt;
 * &emsp;&emsp; <li>网络请求是延时操作，在AsyncTask或子线程中调用执行
 * &emsp;&emsp; <li>getRemoteVersion(String url)获取远程版本
 * <br/>
 * <br/>
 * <b>创建</b>: 2016-2016年6月22日_上午10:57:41 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class HttpRequestUtil {
	
	private HttpRequestUtil(){}
	
	/**
	 * <b>功能</b>：getJsonObject，请求 JsonObject<br/>
	 * <b>说明</b>: 
	 * <li>HttpURLConnection实现
	 * <li>如果网络异常将返回null
	 * <br/>
	 * <br/>
	 * <b>创建</b>：2016年6月22日上午11:02:34<br/>
	 * 
	 * @param url 远程地址
	 * @return jsonObject数据字串。如果网络异常将返回null
	 */
	public static String getJsonObject(String url) throws IOException{
		
		if(IntentStateUtil.getWifiState()){
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setConnectTimeout(60 * 1000);
			conn.setReadTimeout(60 * 1000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			
			if(conn.getResponseCode() == 200){
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String buffer;
				StringBuilder sb = new StringBuilder();
				while((buffer = br.readLine()) != null){
					sb.append(buffer);
				}
				
				br.close();
				
				return sb.toString();
			} 
		}else {
			return "wifi_err";
		}
		
		return null;
	}
}
