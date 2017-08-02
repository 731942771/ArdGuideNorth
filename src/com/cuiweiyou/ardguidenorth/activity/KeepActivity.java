package com.cuiweiyou.ardguidenorth.activity;

import java.util.List;

import com.cuiweiyou.ardguidenorth.R;
import com.cuiweiyou.ardguidenorth.adapter.KeepsAdapter;
import com.cuiweiyou.ardguidenorth.back.IKeepBack;
import com.cuiweiyou.ardguidenorth.bean.DocumentBean;
import com.cuiweiyou.ardguidenorth.task.KeepTask;
import com.cuiweiyou.ardguidenorth.util.JsonUtil;
import com.cuiweiyou.ardguidenorth.util.SQLiteUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * <b>类名</b>: KeepActivity.java， <br/>
 * <b>说明</b>: <br/>
 * <b>创建</b>: 2016-2016年6月23日_下午5:29:50 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class KeepActivity extends Activity implements IKeepBack {

	private ListView mKeep;
	/** 收藏集合 */
	private List<DocumentBean> keeps;
	private TextView mCount;
	protected boolean isRemoved = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keep);

		initView();
		initEvent();
		initData();
	}

	private void initData() {
		new KeepTask(this).execute();
	}

	private void initView() {
		mKeep = (ListView) findViewById(R.id.keep_list);
		mCount = (TextView) findViewById(R.id.keep_count);
	}

	private void initEvent() {
		mKeep.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				createAlterdialog(position);
			}
		});
	}

	@Override
	public void getKeeps(List<DocumentBean> keeps) {
		if (null != keeps && keeps.size() > 0) {
			this.keeps = keeps;

			mCount.setText("收藏数量：" + keeps.size());
			mKeep.setAdapter(new KeepsAdapter(keeps));
		}
	}

	private void createAlterdialog(final int positioner) {
		AlertDialog.Builder builder = new AlertDialog.Builder(KeepActivity.this);
		builder.setTitle("选择操作");
		builder.setMessage("删除收藏或查看。按返回键取消操作");
		
		// 查看按钮
		builder.setPositiveButton("查看", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DocumentBean bean = keeps.get(positioner);
				String json = JsonUtil.document2JsonObject(bean);
				
				Intent i = new Intent();
				i.putExtra("bean", json);
				setResult(2, i);
				
				finish();
			}
		});
		// 删除按钮
		builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DocumentBean bean = keeps.get(positioner);
				if(keeps.remove(bean)){
					isRemoved  = true;
					
					SQLiteUtil.deleteDocument(bean);
					
					mKeep.setAdapter(new KeepsAdapter(keeps));
					mCount.setText("收藏数量：" + keeps.size());
				}
			}
		});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(isRemoved)
				setResult(3);
			
			finish();
		}
		
		return true;
	}
}
