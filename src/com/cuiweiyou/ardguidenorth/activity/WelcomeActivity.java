package com.cuiweiyou.ardguidenorth.activity;

import java.io.IOException;

import com.cuiweiyou.ardguidenorth.R;
import com.cuiweiyou.ardguidenorth.app.RootApplication;
import com.cuiweiyou.ardguidenorth.bean.VersionBean;
import com.cuiweiyou.ardguidenorth.util.ApkUtil;
import com.cuiweiyou.ardguidenorth.util.HttpRequestUtil;
import com.cuiweiyou.ardguidenorth.util.JsonUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/** 
 * <b>类名</b>: WelcomeActivity.java，欢迎界面 <br/>
 * <b>说明</b>: 判断版本更新，及初始化操作，如：SP、网络、数据库、个人配置<br/>
 * <b>创建</b>: 2016-2016年6月21日_上午11:41:05 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class WelcomeActivity extends Activity implements OnClickListener {

	/** 当前版本名称 */
	private String mLocalVersionName;
	/** 当前版本号 */
	private int mLocalVersionCode;
	
	/** 网址链接 */
	private TextView mTvWebsite;
	/** 版本名称显示 */
	private TextView mTvVersionName;
	
	/** 子线程通讯器 */
	private Handler handler;
	/** 最新版本数据的封装体 */
	private VersionBean mNewVersion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		getRemoteVersion(); // 从远处获取最新版本
		initView();			// 实例化控件
		initEvent();		// 定义事件
		initRes();			// 加载必要数据
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		updateView();
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent i = new Intent(WelcomeActivity.this, HomeActivity.class);
				startActivity(i);
				finish();
			}
		}, 3000); // 界面显示，延迟3秒跳转
	}

	
	@Override /** 控件点击事件处理 */
	public void onClick(View v) {
		int id = v.getId();
		
		switch(id){
			case R.id.website: // 网址点击
				Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cuiweiyou.com"));
				startActivity(it);
				
			break;
		}
	}
	
	/**
	 * <b>功能</b>：getRemoteVersion，从远端获取最新版本 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月22日上午10:52:33<br/>
	 */
	private void getRemoteVersion(){
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				int newversion = msg.what;
				if(-1 == newversion){
					Toast.makeText(RootApplication.getAppContext(), "Wifi连接错误", 0).show();
					return;
				}
					
				if(mLocalVersionCode < newversion){
					Toast.makeText(RootApplication.getAppContext(), "有新版本：" + newversion + mNewVersion.getDescription(), 0).show();
					String url = mNewVersion.getUrl();
				}
				
				// TODO 下载更新，延时的进一步精确
			}
		};
		
		// 子线程请求远程数据
		new Thread(){

			public void run() {
				Message msg = handler.obtainMessage();
				int newcode = 0;
				
				try {
					
					String remoteVersion = HttpRequestUtil.getJsonObject("http://www.cuiweiyou.com/guide_north/northapk_newversion.html");//远程 版本相关json
					if(null != remoteVersion){
						if("wifi_err".equals(remoteVersion)){
							newcode = -1;
						} else {
							mNewVersion = JsonUtil.getNewVersion(remoteVersion);
							newcode = mNewVersion.getVersion(); // json解析
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					msg.what = newcode;
					handler.sendMessage(msg);
				}
				
			};
		}.start();
	}
	
	/**
	 * <b>功能</b>：initView，实例化控件 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月22日上午10:30:54<br/>
	 * 
	 * @author cuiweiyou.com
	 */
	private void initView() {
		mTvWebsite = (TextView) findViewById(R.id.website);
		mTvVersionName = (TextView) findViewById(R.id.version);
	}
	
	/**
	 * <b>功能</b>：initEvent，事件处理 <br/>
	 * <b>说明</b>: <br/>
	 * &emsp;&emsp; 网址点击打开浏览器<br/>
	 * <b>创建</b>：2016年6月22日上午10:34:37<br/>
	 * @author cuiweiyou.com
	 */
	private void initEvent(){
		mTvWebsite.setOnClickListener(this);
	}

	/**
	 * <b>功能</b>：init，初始化操作 <br/>
	 * <b>说明</b>: 获取当前版本名称、版本号 <br/>
	 * 
	 * <b>创建</b>：2016年6月22日上午9:54:18<br/>
	 * @author cuiweiyou.com
	 */
	private void initRes() {
		mLocalVersionName = ApkUtil.getVersionName();
		mLocalVersionCode = ApkUtil.getVersionCode();
	}
	
	/**
	 * <b>功能</b>：updateView，更新控件 <br/>
	 * <b>说明</b>: <br/>
	 * &emsp;&emsp; 更新界面的版本名称
	 * <b>创建</b>：2016年6月22日上午10:41:12<br/>
	 * @author cuiweiyou.com
	 */
	private void updateView(){
		mTvVersionName.setText("version_" + mLocalVersionName);
	}
}
