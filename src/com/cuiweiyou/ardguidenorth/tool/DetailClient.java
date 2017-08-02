package com.cuiweiyou.ardguidenorth.tool;

import com.cuiweiyou.ardguidenorth.app.RootApplication;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * <b>类名</b>: DetailClient.java，文档展示webview的客户端 <br/>
 * <b>说明</b>: <br/>
 * <b>创建</b>: 2016-2016年6月22日_下午9:26:44 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class DetailClient extends WebViewClient {
	
	private WebView root;

	public DetailClient(WebView root) {
		this.root = root;
	}

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
    	// Log.e("ard", "加载覆盖");
    	// view.loadUrl(url);	// view即mWebView，url即上面设置的 http://www.cuiweiyou.com
        return true;			// true指打开页面的客户端为本WebView控件自己
    }
    
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
    	super.onReceivedError(view, errorCode, description, failingUrl);
    	
    	Toast.makeText(RootApplication.getAppContext(), "Wifi连接失败", 1).show();
    }

	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
//		Log.e("ard", "加载完毕");
//		root.loadUrl("javascript:abc123.showSource(document.getElementsByTagName('html')[0].innerHTML)");
	}
}
