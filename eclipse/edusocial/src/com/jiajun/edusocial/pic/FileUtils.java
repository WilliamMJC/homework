package com.jiajun.edusocial.pic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
//文件公用方法存放类
public class FileUtils {
	//SDPATH变量是根目录下新建formats目录并指向他作为程序文件根目录
	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/formats/";
	//saveBitmap函数，传入一个Bitmap和一个图片名字符串；
	public static void saveBitmap(Bitmap bm, String picName) {
		Log.e("", "保存图片");
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			//创建一个文件对象，是在formats下创建 图片名。jpeg
			File f = new File(SDPATH, picName + ".JPEG"); 
			//如果这个名字的图片存在就删掉；
			if (f.exists()) {
				f.delete();
			}
			//如果不在场，创建 流文件写出类对象，
			FileOutputStream out = new FileOutputStream(f);
			//传入的bitmap图片的压缩方法，调用jpeg压缩方法，90品质，传入流流文件写出对象
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			//关闭停止 写出流
			out.flush();
			out.close();
			Log.e("", "已经保存");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//创建SD卡目录下的文件夹的函数，传入文件夹名称，返回值File'
	public static File createSDDir(String dirName) throws IOException {
		//formats下文件夹名称的File的对象
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}
	//
	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}
	
	public static void delFile(String fileName){
		File file = new File(SDPATH + fileName);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDir(); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

}
