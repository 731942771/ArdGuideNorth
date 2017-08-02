package com.cuiweiyou.ardguidenorth.activity;

import java.io.IOException;
import java.util.List;

import com.cuiweiyou.ardguidenorth.R;
import com.cuiweiyou.ardguidenorth.adapter.DetailAdapter;
import com.cuiweiyou.ardguidenorth.adapter.KeepsAdapter;
import com.cuiweiyou.ardguidenorth.app.RootApplication;
import com.cuiweiyou.ardguidenorth.back.IDetailBack;
import com.cuiweiyou.ardguidenorth.back.IKeepBack;
import com.cuiweiyou.ardguidenorth.bean.DetailBean;
import com.cuiweiyou.ardguidenorth.bean.DocumentBean;
import com.cuiweiyou.ardguidenorth.bean.VersionBean;
import com.cuiweiyou.ardguidenorth.task.KeepTask;
import com.cuiweiyou.ardguidenorth.task.MenuTask;
import com.cuiweiyou.ardguidenorth.tool.DetailClient;
import com.cuiweiyou.ardguidenorth.tool.JavaScriptConnectToJava;
import com.cuiweiyou.ardguidenorth.util.ApkUtil;
import com.cuiweiyou.ardguidenorth.util.HttpRequestUtil;
import com.cuiweiyou.ardguidenorth.util.IntentStateUtil;
import com.cuiweiyou.ardguidenorth.util.JsonUtil;
import com.cuiweiyou.ardguidenorth.util.SQLiteUtil;
import com.cuiweiyou.ardguidenorth.util.SharedPrefUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <b>类名</b>: HomeActivity.java， 应用主界面<br/>
 * <b>说明</b>: 显示文章内容<br/>
 * <b>创建</b>: 2016-2016年6月21日_下午1:53:30 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class HomeActivity extends Activity implements IDetailBack, OnClickListener, IKeepBack {
	/** 谷歌的抽屉 */
	private DrawerLayout mDrawerLayout;

	/** 文章标题 */
	private TextView mTitle;

	/** 阅读历史和收藏_容器 */
	private View mHistory;
	/** 收藏列表 */
	private ListView mHistoryList;
	/** 阅读历史 */
	private TextView mHistoryTitle;

	/** 工具条 */
	private View mToolbar;
	/** 收藏 */
	private ImageView mToolbarKeep;
	/** 讨论 */
	private View mToolbarTalk;

	/** 文档正文 */
	private WebView mWebView;
	/** webview隐藏 */
	private boolean isWebViewGone = true;

	/** 左侧拉数据集合 */
	private List<DetailBean> details;

	/** 左侧拉抽屉_容器 */
	private View mLeftSlider;
	/** 左侧拉抽屉标题 */
	private TextView mLeftSliderTitle;
	/** 左侧拉抽屉文章列表 */
	private ExpandableListView mLeftSliderMenus;

	/** 右侧拉抽屉 */
	private View mRightSlider;
	/** 查看收藏 */
	private View mCheckKeeps;
	/** 检测更新 */
	private View mCheckUpdate;
	/** 关于 */
	private View mAbout;
	/** 退出按钮 */
	private View mExit;

	/** 上次阅读 */
	private DocumentBean lastDocument;
	/** 正在阅读 */
	private DocumentBean mCurrentDocument;

	/** 上次点击back键 */
	private long lastBack = 0;

	/** 新版本 */
	protected VersionBean mNewVersion;

	/** 更新处理器 */
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		initView();
		initEvent();
		initParams();
		initData();
	}

	/**
	 * <b>功能</b>：initView，实例化控件 <br/>
	 * <b>说明</b>: 定义左右抽屉<br/>
	 * <b>创建</b>：2016年6月22日下午2:13:03<br/>
	 */
	private void initView() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

		mTitle = (TextView) findViewById(R.id.title);

		mHistory = findViewById(R.id.history);
		mHistoryList = (ListView) findViewById(R.id.history_keeps);
		mHistoryTitle = (TextView) findViewById(R.id.history_title);

		mToolbar = findViewById(R.id.toolbar);
		mToolbarKeep = (ImageView) findViewById(R.id.toolbar_keep);
		mToolbarTalk = findViewById(R.id.toolbar_talk);

		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.setOverScrollMode(WebView.OVER_SCROLL_NEVER); // 内容太多时不能被滚动，否则会像ScrollView一样。
		// mWebView.getSettings().setJavaScriptEnabled(true); //
		// 支持js。false可屏蔽电信运营商注入广告
		mWebView.addJavascriptInterface(new JavaScriptConnectToJava(), "abc123"); // 指定JS交互接口
		mWebView.setWebViewClient(new DetailClient(mWebView)); // 指定打开页面的客户端

		mLeftSlider = findViewById(R.id.left_slider);
		mLeftSliderTitle = (TextView) findViewById(R.id.left_slider_title);
		mLeftSliderMenus = (ExpandableListView) findViewById(R.id.left_slider_menus);

		mRightSlider = findViewById(R.id.right_slider);
		mCheckKeeps = findViewById(R.id.right_check_keeps);
		mCheckUpdate = findViewById(R.id.right_check_update);
		mAbout = findViewById(R.id.right_about);
		mExit = findViewById(R.id.right_exit);
	}

	/**
	 * <b>功能</b>：initEvent，为控件加载事件 <br/>
	 * <b>说明</b>: 组点击、文章项点击、上次阅读点击、收藏点击<br/>
	 * <b>创建</b>：2016年6月22日下午7:34:21<br/>
	 */
	private void initEvent() {
		mLeftSliderMenus.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // 子项-文章点击

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				List<DocumentBean> child = details.get(groupPosition).getChild();
				DocumentBean doc = child.get(childPosition);

				// String title = doc.getTitle();
				// String url = doc.getUrl();

				loadDocument(doc);

				if (mDrawerLayout.isDrawerOpen(mLeftSlider)) { // 关闭
					mDrawerLayout.closeDrawer(mLeftSlider);
				}

				return true; // 处理完毕
			}
		});

		mLeftSliderMenus.setOnGroupExpandListener(new OnGroupExpandListener() { // 组-章节点击
			@Override
			public void onGroupExpand(int groupPosition) {
				int count = mLeftSliderMenus.getExpandableListAdapter().getGroupCount();
				for (int i = 0; i < count; i++) {
					if (groupPosition != i) {// 关闭其他分组
						mLeftSliderMenus.collapseGroup(i);
					}
				}
			}
		});

		mHistoryList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				DocumentBean bean = (DocumentBean) mHistoryList.getAdapter().getItem(position);
				loadDocument(bean);
			}
		});

		mHistoryTitle.setOnClickListener(this);
		mToolbarKeep.setOnClickListener(this);
		mToolbarTalk.setOnClickListener(this);

		mCheckKeeps.setOnClickListener(this);
		mCheckUpdate.setOnClickListener(this);
		mAbout.setOnClickListener(this);
		mExit.setOnClickListener(this);
	}

	/**
	 * <b>功能</b>：loadDocument，显示标题并加载页面 <br/>
	 * <b>说明</b>: 更新 标题、webvieww、收藏按钮<br/>
	 * <b>创建</b>：2016年6月23日上午11:44:36<br/>
	 * 
	 * @param bean 当前阅读
	 */
	private void loadDocument(DocumentBean bean) {
		if(null == bean)
			return ;
		
		if (isWebViewGone) {
			isWebViewGone = false;
			mWebView.setVisibility(View.VISIBLE);
			mHistory.setVisibility(View.GONE);
			mHistoryList.setAdapter(null);

			mToolbar.setVisibility(View.VISIBLE);
		}

		mCurrentDocument = bean;
		SharedPrefUtil.saveLastDocument(bean); // 更新最后阅读的文档

		// 读取收藏列表，点亮收藏按钮
		boolean hasDocument = SQLiteUtil.hasDocument(bean);
		if (hasDocument) {
			mToolbarKeep.setImageResource(R.drawable.keep_yes);
			mToolbarKeep.setTag("yes");
		} else {
			mToolbarKeep.setImageResource(R.drawable.keep_no);
			mToolbarKeep.setTag("no");
		}

		mTitle.setText("《安卓开发指北》" + bean.getTitle()); // 更新标题
		mWebView.loadUrl(bean.getUrl()); // 指定网络地址加载文章
	}

	/** View点击 */
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.history_title: { // 上次阅读
	
				loadDocument(lastDocument);
	
				break;
			}
	
			case R.id.toolbar_keep: { // 收藏按钮
	
				if ("no".equals(mToolbarKeep.getTag().toString())) {
					mToolbarKeep.setImageResource(R.drawable.keep_yes);
					mToolbarKeep.setTag("yes");
	
					SQLiteUtil.saveDocument(mCurrentDocument);
				} else {
					mToolbarKeep.setImageResource(R.drawable.keep_no);
					mToolbarKeep.setTag("no");
	
					SQLiteUtil.deleteDocument(mCurrentDocument);
				}
	
				break;
			}
	
			case R.id.right_check_keeps: { // 查看收藏
	
				Intent i = new Intent(HomeActivity.this, KeepActivity.class);
				startActivityForResult(i, 0);
	
				if (mDrawerLayout.isDrawerOpen(mRightSlider)) { // 关闭右侧拉
					mDrawerLayout.closeDrawer(mRightSlider);
				}
	
				break;
			}
	
			case R.id.right_check_update: { // TODO 检测更新
				getRemoteVersion();
				
				break;
			}
	
			case R.id.right_about: { // 关于
				
				Intent i = new Intent(HomeActivity.this, AboutActivity.class);
				startActivity(i);
	
				if (mDrawerLayout.isDrawerOpen(mRightSlider)) { // 关闭右侧拉
					mDrawerLayout.closeDrawer(mRightSlider);
				}
	
				break;
			}
	
			case R.id.right_exit: { // 退出应用
				exitApp();
				
				break;
			}
			
			case R.id.toolbar_talk: { // TODO 讨论
				Toast.makeText(RootApplication.getAppContext(), "老崔正在加紧开发此功能", 0).show();
				
				break;
			}
		}
	}

	/**
	 * <b>功能</b>：initParams，必要的初始化 <br/>
	 * <b>说明</b>: 更新抽屉宽度<br/>
	 * <b>创建</b>：2016年6月22日下午2:12:29<br/>
	 */
	private void initParams() {
		int width = getWindowManager().getDefaultDisplay().getWidth();

		DrawerLayout.LayoutParams lpLeft = (DrawerLayout.LayoutParams) mLeftSlider.getLayoutParams();
		lpLeft.width = width / 4 * 3;
		DrawerLayout.LayoutParams lpRight = (DrawerLayout.LayoutParams) mRightSlider.getLayoutParams();
		lpRight.width = width / 4 * 3;

		lastDocument = SharedPrefUtil.getLastDocument();
		if (null != lastDocument) {
			mHistoryTitle.setText(lastDocument.getGroup() + " - " + lastDocument.getTitle() + " | " + lastDocument.getUrl());
		}
	}

	/**
	 * <b>功能</b>：initData，左侧拉列表加载数据 <br/>
	 * <b>说明</b>: 网络请求或本地加载。延迟操作<br/>
	 * <b>创建</b>：2016年6月22日下午5:11:46<br/>
	 */
	private void initData() {
		new MenuTask(this).execute();
		new KeepTask(this).execute();
	}

	/**
	 * 远程请求目录完毕。由{@link #initData }启动
	 */
	@Override
	public void getDetails(List<DetailBean> detail) {
		this.details = detail;

		if (null != detail && detail.size() > 0) {
			mLeftSliderMenus.setAdapter(new DetailAdapter(detail));
		}
	}

	/**
	 * 拿到收藏列表。由{@link #initData }启动
	 */
	@Override
	public void getKeeps(List<DocumentBean> keeps) {
		if (null != keeps && keeps.size() > 0) {
			((TextView) findViewById(R.id.history_keeps_count)).setText("收藏数量：" + keeps.size());
			mHistoryList.setAdapter(new KeepsAdapter(keeps));
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mDrawerLayout.isDrawerOpen(mLeftSlider)) { // 关闭左侧拉
				mDrawerLayout.closeDrawer(mLeftSlider);

			} else if (mDrawerLayout.isDrawerOpen(mRightSlider)) { // 关闭右侧拉
				mDrawerLayout.closeDrawer(mRightSlider);

			} else {
				exitApp();
			}
		}

		return true;
	}

	/**
	 * <b>功能</b>：exitApp，双击退出应用 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月23日下午7:43:52<br/>
	 *
	 */
	private void exitApp() {
		long now = System.currentTimeMillis();
		if ((now - lastBack) < 500) {
			finish();

		} else {
			Toast.makeText(RootApplication.getAppContext(), "500ms内再次点击退出应用", 0).show();
			lastBack = now;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (2 == resultCode && null != data) {
			String json = data.getStringExtra("bean");
			DocumentBean bean = JsonUtil.jsonObject2Document(json);
			if (null != bean) {
				loadDocument(bean);
			}
		}

		if (3 == resultCode) {
			if (isWebViewGone) {
				new KeepTask(this).execute();
			} else {
				boolean has = SQLiteUtil.hasDocument(mCurrentDocument);
				if (has) {
					mToolbarKeep.setImageResource(R.drawable.keep_yes);
					mToolbarKeep.setTag("yes");
				} else {
					mToolbarKeep.setImageResource(R.drawable.keep_no);
					mToolbarKeep.setTag("no");
				}
			}
		}
	}
	/**
	 * <b>功能</b>：getRemoteVersion，从远端获取最新版本 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月22日上午10:52:33<br/>
	 */
	private void getRemoteVersion(){
		
		final int mLocalVersionCode = ApkUtil.getVersionCode();
		
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
				} else {
					Toast.makeText(RootApplication.getAppContext(), "老崔很忙，还未开发新版本", 0).show();
				}
				
				// TODO 下载更新，异步，通知
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
	
	@Override
	protected void onResume() {
		super.onResume();
		
		boolean wifiState = IntentStateUtil.getWifiState();
		if(!wifiState){
			mTitle.setText(Html.fromHtml("<font color='#ff0000'>Wifi连接错误</font>"));
		}
	}
}
