package com.cuiweiyou.ardguidenorth.bean;

/**
 * <b>类名</b>: VersionBean.java，最新版本数据的封装体 <br/>
 * <b>说明</b>: <br/>
 * <b>创建</b>: 2016-2016年6月22日_下午12:05:04 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class VersionBean {
	int version;
	String description;
	String url;

	/**
	 * <b>功能</b>：VersionBean.java，最新版本数据的封装体 <br/>
	 * <b>创建</b>：2016年6月22日下午12:06:50<br/>
	 * 
	 * @param version 版本号
	 * @param description 描述
	 * @param url 新apk地址
	 * @author cuiweiyou.com
	 */
	public VersionBean(int version, String description, String url) {
		super();
		this.version = version;
		this.description = description;
		this.url = url;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "VersionBean [version=" + version + ", description=" + description + ", url=" + url + "]";
	}

}
