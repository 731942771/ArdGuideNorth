package com.cuiweiyou.ardguidenorth.task;

import java.io.IOException;
import java.util.List;

import com.cuiweiyou.ardguidenorth.back.IDetailBack;
import com.cuiweiyou.ardguidenorth.bean.DetailBean;
import com.cuiweiyou.ardguidenorth.util.HttpRequestUtil;
import com.cuiweiyou.ardguidenorth.util.JsonUtil;

import android.os.AsyncTask;
import android.util.Log;

/**
 * <b>类名</b>: MenuTask.java，左侧拉目录请求 <br/>
 * <b>说明</b>: 文章列表 <br/>
 * <b>创建</b>: 2016-2016年6月22日_下午5:13:00 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class MenuTask extends AsyncTask<Void, Void, List<DetailBean>>{
	/** 目录回调器 */
	private IDetailBack back;

	public MenuTask(IDetailBack back) {
		this.back = back;
	}

	@Override
	protected List<DetailBean> doInBackground(Void... params) {
		List<DetailBean> detailList = null;
		
		try {
			String detail  = HttpRequestUtil.getJsonObject("http://www.cuiweiyou.com/guide_north/detail.html");
			
			if(null != detail){
				detailList = JsonUtil.getDetailList(detail);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return detailList;
	}

	@Override
	protected void onPostExecute(List<DetailBean> result) {
		super.onPostExecute(result);
		
		back.getDetails(result);
	}
}
