package com.webserver;
/**
 * 返回给客户端结果
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年7月3日
 */

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultPrint {
	private static final Logger log = LoggerFactory.getLogger(ResultPrint.class);
	private OutputStream os;
	private Response response;

	public ResultPrint(OutputStream os, Response response) throws IOException {
		this.os = os;
		this.response = response;
		PrintResult();
	}

	/*
	 * 返回最后的响应结果
	 */
	private void PrintResult() throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(os);
		// 获取响应头部
		byte[] header = response.getHeader();
		// 获取响应内容
		byte[] content = response.getResponseContent();
		try {
			bos.write(header);
		} catch (IOException e) {
			log.error("返回响应头出错", e);
			throw e;
		}
		try {
			bos.write(content);
			bos.flush();
		} catch (IOException e) {
			log.error("返回响应内容出错", e);
			throw e;
		}
	}

}
