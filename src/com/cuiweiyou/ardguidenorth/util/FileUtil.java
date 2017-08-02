package com.cuiweiyou.ardguidenorth.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.cuiweiyou.ardguidenorth.app.RootApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * 
 * <b>类名</b>: FileUtil.java，文件系统工具 <br/>
 * <b>说明</b>: <br/>
 * <li>&lt;uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" /&gt;
 * <li>&lt;uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /&gt;
 * <li>&lt;uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /&gt;
 * <br/>
 * <br/>
 * <b>创建</b>: 2016-2016年6月22日_下午1:15:39 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class FileUtil {
	
	/**
	 * 检查SD卡是否可用
	 * @return
	 */
	private boolean isSDCardOK(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}
		return false;
	}

	/**
	 * 保存图片到本地缓存
	 * @param context 上下文
	 * @param bmp     Bitmap图片数据
	 * @param imgPath 图片名。是网络全路径名称
	 * @throws IOException 
	 */
	public void setCacheBitmap(Context context, Bitmap bmp, String imgPath) throws IOException{
		if(bmp == null){
			return;
		}
		if(imgPath == null){
			return;
		}
		if(!isSDCardOK()){
			return;
		}

		// http://uploadfile.qikan.com.cn/Files/Public/Compilation/Uploads/m/20140212103704.jpg
		imgPath = imgPath.replace("/", "|");
		// android3.2禁止目录里有冒号
		imgPath = imgPath.replace(":", "_");
		
		// 目标存储全路径 /mnt/sdcade/android/data/包/cache/"imgPath"
		String pointPath = context.getExternalCacheDir().getAbsolutePath()+"/"+imgPath;

		// 1 字节输出流
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		
		// 2 图片压缩（格式，原质量的百分比，目标输出流），主要目的是转为jpg图片
		bmp.compress(CompressFormat.JPEG, 60, bo);
		
		// 3 拥有了图片数据的输出流转为字节数组
		byte[]buffer = bo.toByteArray();
		
		// 4 写到文件
		FileOutputStream out = new FileOutputStream(pointPath);
		out.write(buffer);
		out.flush();
		out.close();
	}

	/**
	 * 提取本地缓存图片
	 * @param context 上下文
	 * @param imgPath  图片。是网络全路径名称
	 * @return Bitmap 图片Bitmap
	 */
	public Bitmap getCacheBitmap(Context context, String imgPath){
		
		Bitmap bitmap = null;
		
		if(imgPath == null)
			return bitmap;
		if(!isSDCardOK())
			return bitmap;
		
		imgPath = imgPath.replace("/", "|");
		imgPath = imgPath.replace(":", "_");
		
		// /mnt/sdcade/android/data/包/cache
		String pointPath = context.getExternalCacheDir().getAbsolutePath()+"/"+imgPath;
		File file = new File(pointPath);
		if(file.exists()){
			bitmap = BitmapFactory.decodeFile(pointPath);
		}
		
		return bitmap;
	}
	
	/**
	 * 存储首页资讯列表数据到内存卡
	 * @param context 上下文
	 * @param value json数据
	 * @param name 文件名。 json请求地址
	 */
	public void setCacheJson(String value, String name){
		if(value == null)
			return;
		if(name == null)
			return;
		if(!isSDCardOK())
			return;
		
		name = name.replace("/", "|");
		name = name.replace(":", "_");
		
		String jsonPath = RootApplication.getAppContext().getExternalCacheDir().getAbsolutePath() + "/" + name + ".txt";
		try {
			FileOutputStream out = new FileOutputStream(jsonPath);
			byte[]buffer = value.getBytes();
			out.write(buffer);
			out.flush();
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 提取本地json数据 
	 * @param context 上下文
	 * @param name  文件名。 json请求地址
	 * @return json字串
	 */
	public String getCacheJson(String name){
		if(name == null)
			return null;
		if(!isSDCardOK())
			return null;
		
		String result = null;

		name = name.replace("/", "|");
		name = name.replace(":", "_");
		
		String jsonPath = RootApplication.getAppContext().getExternalCacheDir().getAbsolutePath() + "/" + name + ".txt";
		if(new File(jsonPath).exists()){
			try {
				FileInputStream in = new FileInputStream(jsonPath);
				InputStreamReader ir = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(ir);
				StringBuffer sb = new StringBuffer();
				String buffer = null;
				while((buffer = br.readLine()) != null){
					sb.append(buffer);
				}
				result = sb.toString();
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

}