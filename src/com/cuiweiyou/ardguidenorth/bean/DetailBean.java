package com.cuiweiyou.ardguidenorth.bean;

import java.util.List;

/**
 * <b>类名</b>: DetailBean.java，目录分组封装体 <br/>
 * <b>说明</b>: <br/>
 * <b>创建</b>: 2016-2016年6月22日_下午6:31:26 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class DetailBean {

	String group;
	List<DocumentBean> child;

	/**
	 * <b>功能</b>：DetailBean.java，目录数据封装体 <br/>
	 * <b>创建</b>：2016年6月22日下午6:34:43<br/>
	 * 
	 * @param group 分组，大的章节
	 * @param child 章节下的文章列表
	 * @author cuiweiyou.com
	 */
	public DetailBean(String group, List<DocumentBean> child) {
		super();
		this.group = group;
		this.child = child;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public List<DocumentBean> getChild() {
		return child;
	}

	public void setChild(List<DocumentBean> child) {
		this.child = child;
	}

	@Override
	public String toString() {
		return "DetailBean [group=" + group + ", child=" + child + "]";
	}

}
