package com.cuiweiyou.ardguidenorth.task;

import java.util.List;

import com.cuiweiyou.ardguidenorth.back.IKeepBack;
import com.cuiweiyou.ardguidenorth.bean.DocumentBean;
import com.cuiweiyou.ardguidenorth.util.SQLiteUtil;

import android.os.AsyncTask;

/**
 * <b>类名</b>: KeepTask.java，收藏列表请求 <br/>
 * <b>创建</b>: 2016-2016年6月23日_下午1:13:00 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class KeepTask extends AsyncTask<Void, Void, List<DocumentBean>>{
	/** 目录回调器 */
	private IKeepBack back;

	public KeepTask(IKeepBack back) {
		this.back = back;
	}

	@Override
	protected List<DocumentBean> doInBackground(Void... params) {
		List<DocumentBean> keepList = null;
		
		keepList = SQLiteUtil.getAllKeep();
		
		return keepList;
	}

	@Override
	protected void onPostExecute(List<DocumentBean> result) {
		super.onPostExecute(result);
		
		back.getKeeps(result);
	}
}
