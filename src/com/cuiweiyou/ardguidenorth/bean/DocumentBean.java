package com.cuiweiyou.ardguidenorth.bean;

/**
 * <b>类名</b>: DocumentBean.java，目录下文档数据封装 <br/>
 * <b>说明</b>: <br/>
 * <b>创建</b>: 2016-2016年6月22日_下午6:33:30 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class DocumentBean {
	String group;
	String title;
	String url;

	/**
	 * <b>功能</b>：DocumentBean.java，目录下文档数据封装 <br/>
	 * <b>创建</b>：2016年6月22日下午6:35:52<br/>
	 * 
	 * @param group 章节
	 * @param title 文章标题
	 * @param url 在线地址
	 * @author cuiweiyou.com
	 */
	public DocumentBean(String group, String title, String url) {
		super();
		this.group = group;
		this.title = title;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "DocumentBean [group=" + group + ", title=" + title + ", url=" + url + "]";
	}

}
