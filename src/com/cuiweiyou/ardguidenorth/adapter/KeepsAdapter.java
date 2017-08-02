package com.cuiweiyou.ardguidenorth.adapter;

import java.util.List;

import com.cuiweiyou.ardguidenorth.R;
import com.cuiweiyou.ardguidenorth.app.RootApplication;
import com.cuiweiyou.ardguidenorth.bean.DocumentBean;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * <b>类名</b>: KeepsAdapter.java，收藏列表适配器 <br/>
 * <b>说明</b>: <br/>
 * <b>创建</b>: 2016-2016年6月23日_下午1:49:35 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class KeepsAdapter extends BaseAdapter {
	
	private List<DocumentBean> list;

	public KeepsAdapter(List<DocumentBean> keeps) {
		this.list = keeps;
	}

	@Override
	public int getCount() {
		return null == list || list.size() == 0 ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(null == convertView){
			convertView = View.inflate(RootApplication.getAppContext(), R.layout.item_keep, null);
		}
		
		String group = list.get(position).getGroup();
		String title = list.get(position).getTitle();
		String url = list.get(position).getUrl();
		
		TextView keeper = (TextView) convertView.findViewById(R.id.item_keeper);
		keeper.setText(group + " - " + title + " | " + url);
		
		return convertView;
	}

}
