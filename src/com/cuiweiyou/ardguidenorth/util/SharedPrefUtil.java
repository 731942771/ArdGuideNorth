package com.cuiweiyou.ardguidenorth.util;

import com.cuiweiyou.ardguidenorth.app.RootApplication;
import com.cuiweiyou.ardguidenorth.bean.DocumentBean;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * <b>类名</b>: SharedPrefUtil.java，小数据存储SP工具 <br/>
 * <b>说明</b>: <br/>
 * <b>创建</b>: 2016-2016年6月22日_下午11:01:29 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class SharedPrefUtil {

	private static SharedPreferences mHistorySP;

	private SharedPrefUtil(){
	}
	
	/**
	 * <b>功能</b>：saveLastDocument，上次阅读文档 <br/>
	 * <b>说明</b>: 文件名为history，key为last_read_bean <br/>
	 * <b>创建</b>：2016年6月22日下午11:01:58<br/>
	 * @param bean 文档对象
	 */
	public static void saveLastDocument(DocumentBean bean){
		if(null == mHistorySP)
			mHistorySP = RootApplication.getAppContext().getSharedPreferences("history", 0);
		
		String docstr = JsonUtil.document2JsonObject(bean);
		
		if(null == docstr)
			docstr = "null";
		
		Editor edit = mHistorySP.edit();
		edit.putString("last_read_bean", docstr);
		edit.commit();
	}
	
	/**
	 * <b>功能</b>：getLastDocument， <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月23日上午10:19:10<br/>
	 * 
	 * @return DocumentBean 对象，错误时为null
	 */
	public static DocumentBean getLastDocument(){
		if(null == mHistorySP)
			mHistorySP = RootApplication.getAppContext().getSharedPreferences("history", 0);
		
		DocumentBean doc = null;
		
		String str = mHistorySP.getString("last_read_bean", null);
		if(null != str){
			doc = JsonUtil.jsonObject2Document(str);
		}
		
		return doc;
	}
}
