package com.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件的工具类，用于保存文件
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年7月5日
 */
public class FileOperation {
	private static final Logger log = LoggerFactory.getLogger(FileOperation.class);
	/**
	 * 保存文件
	 * @param absolutePath 文件的绝对路径
	 * @param content 文件的内容
	 * @throws IOException 
	 */
	public void saveFile(String absolutePath, String content) throws IOException {
		File file = new File(absolutePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			try {
				fos.write(content.getBytes(Coding.DEFALUT_CODING));
			} catch (UnsupportedEncodingException e) {
				log.error("不支持的编码", e);
				throw new UnsupportedEncodingException("不支持的编码");
			} catch (IOException e) {
				log.error("写入文件失败", e);
				throw new IOException("写入文件失败",e);
			}
		} catch (FileNotFoundException e) {
			log.error("未找到文件", e);
			throw new FileNotFoundException("未找到文件");
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					log.error("输出流关闭失败", e);
					throw new IOException("输出流关闭失败",e);
				}
			}
		}
	}
}
