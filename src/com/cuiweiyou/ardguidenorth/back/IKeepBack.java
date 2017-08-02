package com.cuiweiyou.ardguidenorth.back;

import java.util.List;

import com.cuiweiyou.ardguidenorth.bean.DocumentBean;

/**
 * <b>类名</b>: IKeepBack.java，收藏列表回调 <br/>
 * <b>说明</b>: <br/>
 * <b>创建</b>: 2016-2016年6月23日_下午1:39:12 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public interface IKeepBack {

	/**
	 * <b>功能</b>：getKeeps，收藏列表回调 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月23日下午1:39:24<br/>
	 * 
	 * @param detail 全部的收藏
	 */
	public void getKeeps(List<DocumentBean> keeps);
}
