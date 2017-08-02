package com.cuiweiyou.ardguidenorth.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cuiweiyou.ardguidenorth.bean.DetailBean;
import com.cuiweiyou.ardguidenorth.bean.DocumentBean;
import com.cuiweiyou.ardguidenorth.bean.VersionBean;

/**
 * <b>类名</b>: JsonUtil.java，JSON解析工具 <br/>
 * <b>说明</b>: <br/>
 * <b>创建</b>: 2016-2016年6月22日_上午11:53:19 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class JsonUtil {

	private JsonUtil(){}
	
	/**
	 * <b>功能</b>：getNewVersion，将jsonObject解析为bean <br/>
	 * <b>说明</b>: 解析失败返回null<br/>
	 * <b>创建</b>：2016年6月22日下午12:03:58<br/>
	 * 
	 * @param json jsonObject数据
	 * 
	 * @return VersionBean 版本信息封装体 <br/>
	 */
	public static VersionBean getNewVersion(String json){
		
		try {
			JSONObject jobj = new JSONObject(json);
			int version = jobj.getInt("version");
			String description = jobj.getString("description");
			String url = jobj.getString("url");
			
			return new VersionBean(version, description, url);
		} catch (JSONException e) {
			e.printStackTrace();
			
			return null;
		}
	}

	/**
	 * <b>功能</b>：getDetailList，解析目录列表 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月22日下午6:37:32<br/>
	 * 
	 * @param detail 目录的json数据
	 * @return List<DetailBean> 封装后的目录列表
	 */
	public static List<DetailBean> getDetailList(String detail) {
		List<DetailBean> list = new ArrayList<DetailBean>();
		
		try {
			JSONObject jobj = new JSONObject(detail);
			String result = jobj.getString("result");
			
			JSONArray jarr = new JSONArray(result);
			int size = jarr.length();
			for (int i = 0; i < size; i++) {
				List<DocumentBean> childList = new ArrayList<DocumentBean>();
				
				JSONObject obj = jarr.getJSONObject(i);
				String group = obj.getString("group");
				JSONArray child = obj.getJSONArray("child");
				int length = child.length();
				for (int j = 0; j < length; j++) {
					JSONObject jsonObject = child.getJSONObject(j);
					String title = jsonObject.getString("title");
					String url = jsonObject.getString("url");
					
					childList.add(new DocumentBean(group, title, url));
				}
				
				list.add(new DetailBean(group, childList));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * <b>功能</b>：document2JsonObject，文档对象转json字串 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月22日下午11:05:40<br/>
	 * 
	 * @param doc 文档bean
	 */
	public static String document2JsonObject(DocumentBean doc) {
		if(null == doc)
			return null;
		
		String group = doc.getGroup();
		String title = doc.getTitle();
		String url = doc.getUrl();
		
		JSONObject jo = new JSONObject();
		try {
			jo.put("group", group);
			jo.put("title", title);
			jo.put("url", url);
		} catch (JSONException e) {
			e.printStackTrace();
		}
				
		return jo.toString();
	}

	/**
	 * <b>功能</b>：jsonObject2Document，转换jsonobject为document对象 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月23日上午10:22:26<br/>
	 * 
	 * @param str json字串
	 * @return bean对象
	 */
	public static DocumentBean jsonObject2Document(String str) {
		if(null == str || "".equals(str))
			return null;
		
		DocumentBean bean = null;

		try {
			JSONObject jobj = new JSONObject(str);
			String group = jobj.getString("group");
			String title = jobj.getString("title");
			String url = jobj.getString("url");
			bean = new DocumentBean(group, title, url);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return bean;
	}

}
