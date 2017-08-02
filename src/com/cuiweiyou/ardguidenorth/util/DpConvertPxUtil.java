package com.cuiweiyou.ardguidenorth.util;

import android.content.Context;

/**
 * DP和PX相互转换
 */
public class DpConvertPxUtil {
	
	/**
	 * dp转px
	 */
	public static int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * px转dp
	 */
	public static int px2dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
}
