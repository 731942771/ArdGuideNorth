package com.cuiweiyou.ardguidenorth.back;

import java.util.List;

import com.cuiweiyou.ardguidenorth.bean.DetailBean;

/**
 * <b>类名</b>: IDetailBack.java，目录回调接口 <br/>
 * <b>说明</b>: <br/>
 * <b>创建</b>: 2016-2016年6月22日_下午6:51:00 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public interface IDetailBack {
	/**
	 * <b>功能</b>：getDetails，目录回调接口 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月22日下午7:02:47<br/>
	 * 
	 * @param detail 分组列表
	 */
	public void getDetails(List<DetailBean> detail);
}
