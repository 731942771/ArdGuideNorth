package com.cuiweiyou.ardguidenorth.adapter;

import java.util.List;

import com.cuiweiyou.ardguidenorth.R;
import com.cuiweiyou.ardguidenorth.app.RootApplication;
import com.cuiweiyou.ardguidenorth.bean.DetailBean;
import com.cuiweiyou.ardguidenorth.bean.DocumentBean;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * <b>类名</b>: DetailAdapter.java，左侧拉目录适配器 <br/>
 * <b>说明</b>: <br/>
 * <b>创建</b>: 2016-2016年6月22日_下午7:01:40 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class DetailAdapter extends BaseExpandableListAdapter {

	private List<DetailBean> list;

	public DetailAdapter(List<DetailBean> detail) {
		this.list = detail;
	}
	
	/** 获取本适配器的数据集合 **/
	public List<DetailBean> getList(){
		return list;
	}

	@Override
	public int getGroupCount() { // 外层父条目总数
		return list == null || list.size() < 1 ? 0 : list.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) { // 内层子条目总数
		List<DocumentBean> child = list.get(groupPosition).getChild();
		return null == child || child.size() < 1 ? 0 : child.size();
	}

	@Override
	public Object getGroup(int groupPosition) { // 外层父条目bean
		return list.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) { // 内层子条目bean
		return list.get(groupPosition).getChild().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) { // 外层父条目id
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) { // 内层子条目id
		return childPosition;
	}

	@Override
	public boolean hasStableIds() { // 显示时复用已有对象
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		
	    if(convertView == null){
	        convertView = View.inflate(RootApplication.getAppContext(), R.layout.item_detail_group, null);
	    }
	    
	    TextView mGroup = (TextView) convertView.findViewById(R.id.item_group);    // 组名
	    mGroup.setText(list.get(groupPosition).getGroup());
	    
	    return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null){
	        convertView = View.inflate(RootApplication.getAppContext(), R.layout.item_detail_child, null);
	    }
		
	    TextView mName = (TextView) convertView.findViewById(R.id.item_child);
	    mName.setText(list.get(groupPosition).getChild().get(childPosition).getTitle());
	    
	    return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) { // 内层子条目是否接收点击事件
		return true; // 响应onChildClick
	}
}
