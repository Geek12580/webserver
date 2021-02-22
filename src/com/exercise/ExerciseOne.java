package com.exercise;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 学习基础开发——练习题1
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年6月24日
 */
public class ExerciseOne {
	private static final Logger log = LoggerFactory.getLogger(ExerciseOne.class);

	/**
	 * 将文件内容转换成byte数组返回,如果文件不存在或者读入错误返回null
	 * @throws IOException 
	 */
	public static byte[] file2buf(File fobj) throws IOException {
		// 文件不存在或者是文件夹，返回null
		if (fobj == null || !fobj.exists() || fobj.isDirectory()) {
			return null;
		}
		FileInputStream inputStream = null;// 文件读取流
		byte[] byteArray = null;// 最终返回的byte数组
		try {
			inputStream = new FileInputStream(fobj);// 用文件读取流读取文件
			int length = (int) fobj.length();
			byteArray = new byte[length];
			log.info("文件大小     " + length);
			// 记录当前读取字节位置
			int cur = 0;
			while (cur < length) {
				cur += inputStream.read(byteArray, cur, length - cur);
			}
		} catch (IOException e) {
			log.error("文件读取失败", e);
//			throw new IOException("文件读取失败",e);
			throw e;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();// 关闭文件读取流
				} catch (IOException e) {
					log.error("输入流关闭失败", e);
					throw new IOException("输入流关闭失败",e);
				}
			}
		}
		return byteArray;
	}
}
