package com.cuiweiyou.ardguidenorth.util;

import java.util.ArrayList;
import java.util.List;

import com.cuiweiyou.ardguidenorth.app.RootApplication;
import com.cuiweiyou.ardguidenorth.bean.DocumentBean;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * <b>类名</b>: SQLiteUtil.java，数据库工具 <br/>
 * <b>说明</b>: 存储收藏文档<br/>
 * <b>创建</b>: 2016-2016年6月23日_下午12:53:16 <br/>
 * 
 * @author cuiweiyou.com 
 */
public class SQLiteUtil {
	
	/** 数据库操作助手 */
	private static SQLiteOpenHelper mHelper;

	private SQLiteUtil() {}
	
	/**
	 * <b>功能</b>：saveDocument，保存收藏的文档 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月23日下午12:55:38<br/>
	 *@param bean 保存目标
	 */
	public static void saveDocument(DocumentBean bean) {
		if(null == mHelper)
			createHelper();
	    
	    SQLiteDatabase wdb = mHelper.getWritableDatabase();	// 获取可写的数据库
//	    wdb.execSQL("insert into tb (grouper, title, url) values ('" + bean.getGroup() + "', '" + bean.getTitle() + "', '" + bean.getUrl() + "')");  // 插入

	    ContentValues cv = new ContentValues();
	    cv.put("grouper", bean.getGroup());			// 列名，值
	    cv.put("title", bean.getTitle());			// 列名，值
	    cv.put("url", bean.getUrl());				// 这是个Map，即相同的key，value会覆盖
	    long nums = wdb.insert("tb", null, cv);		// 返回最新插入一条的ID
	    
	    wdb.close();
	}
	
	/**
	 * <b>功能</b>：deleteDocument，删除收藏 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月23日下午1:17:10<br/>
	 * 
	 * @param bean 要删除的文章
	 */
	public static void deleteDocument(DocumentBean bean) {
		if(null == mHelper)
			createHelper();
		
		SQLiteDatabase wdb = mHelper.getWritableDatabase();						// 获取可写的数据库
//		wdb.execSQL("delete from tb where title = '" + bean.getTitle() + "'");	// 删除
		wdb.delete("tb", "title = ?", new String[]{bean.getTitle()});
		
		wdb.close();
	}
	
	/**
	 * <b>功能</b>：hasDocument，是否已有收藏 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月23日下午1:01:10
	 * @param bean 检测目标
	 */
	public static boolean hasDocument(DocumentBean bean){
	    if(null == mHelper)
	    	createHelper();
	    
	    if(null == bean)
	    	return false;
	    
	    String title = bean.getTitle();
	    
	    SQLiteDatabase rdb = mHelper.getReadableDatabase(); // 获取只读类型的数据库 
	    // api形式。（                                 表，        列       where条件，           条件值，                                 groupby，having，orderby）
	    Cursor cursor = rdb.query("tb", null, "title = ?", new String[]{title}, null, null, null);
	    int count = cursor.getCount();
	    
	    cursor.close();
	    rdb.close();
	    
	    if(count > 0)
	    	return true;
	    
	    return false;
	}
	
	/**
	 * <b>功能</b>：getCount，收藏总数 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月23日下午1:06:18 
	 */
	public static int getCount(){
		if(null == mHelper)
			createHelper();
		
	    SQLiteDatabase rdb = mHelper.getReadableDatabase(); // 获取只读类型的数据库 
	    Cursor cursor = rdb.rawQuery("select count(*) count from tb", null);// sql查询
	    int i = cursor.getColumnIndex("count");
	    int count = cursor.getInt(i);
	    
	    cursor.close();
	    rdb.close();
	    
	    return count;
	}

	/**
	 * <b>功能</b>：getAllKeep，查询出全部的收藏 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月23日下午1:42:16<br/>
	 * 
	 * @return List<DocumentBean> 收藏列表
	 */
	public static List<DocumentBean> getAllKeep() {
		if(null == mHelper)
			createHelper();
		
		List<DocumentBean> list = new ArrayList<DocumentBean>();
	    
	    SQLiteDatabase rdb = mHelper.getReadableDatabase();	// 获取只读类型的数据库
	    // sql查询
	    Cursor cursor = rdb.rawQuery("select * from tb", null);
	    while (cursor.moveToNext()) {
	        int _idIndex = cursor.getColumnIndex("_id");
	        int groupIndex = cursor.getColumnIndex("grouper");
	        int titleIndex = cursor.getColumnIndex("title");
	        int urlIndex = cursor.getColumnIndex("url");
	        
	        int id = cursor.getInt(_idIndex); // 得到一条结果中此列的值
	        String group = cursor.getString(groupIndex);
	        String title = cursor.getString(titleIndex);
	        String url = cursor.getString(urlIndex);
	        
	        list.add(new DocumentBean(group, title, url));
	    }
	    
	    cursor.close();
	    rdb.close();
		
		return list;
	}

	/** 
	 * <b>功能</b>：createHelper，创建数据库助手 <br/>
	 * <b>说明</b>: <br/>
	 * <b>创建</b>：2016年6月23日下午12:57:29<br/>
	 */
	private static void createHelper() {
	    /*  context：上下文
	     *  name：数据库文件
	     *  factory：工厂。仍旧null
	     *  version：本次创建数据库的版本-始于1。
	     *         如果原有的版本低，onUpgrade 方法执行对本次创建的数据库升级; 
	     *         如果原有的版本高，onDowngrade 对本次的数据库降级。 */
	    mHelper = new SQLiteOpenHelper(RootApplication.getAppContext(), "north.db", null, 1) {
	        @Override
	        public void onCreate(SQLiteDatabase db) {
	            // 如果表数据将来用于SimpleCursorAdapter，注意其只识别带下划线的主键。 _id 主键
	            db.execSQL("CREATE TABLE tb (_id INTEGER PRIMARY KEY, grouper VARCHAR, title VARCHAR, url VARCHAR);");
	        }
	        
	        // oldVersion：原有数据库的版本，newVersion：新版本
	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	            if (newVersion > oldVersion){
//	                db.execSQL("-- v");    // 备份原表tb的数据到临时表tmp，重构tb表，导入tmp数据到tb，删除tmp
	                   Log.e("ard", "升级了");
	              }
	        }
	        
	        @Override
	        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	            if(oldVersion > newVersion){
//	                db.execSQL("-- v");    // 翻新表
	                   Log.e("ard", "降级了");
	            }
	        }
	    };
	}
}
