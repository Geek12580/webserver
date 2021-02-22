package com.webserver;

import java.io.UnsupportedEncodingException;

/**
 * 封装http响应的接口类
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年7月3日
 */
public interface Response {
	// 返回响应内容
	public byte[] getResponseContent();

	// 设置响应内容
	public void setResponseContent(byte[] responseContent);

	// 获取响应类型
	public String getContentType();

	// 拼接响应头部

	public void setHeader() throws UnsupportedEncodingException;

	// 获取响应头部
	public byte[] getHeader();

	// 获取响应协议
	public String getProtocol();

	// 设置响应协议
	public void setProtocol(String protocol);

	// 获取状态码
	public int getStatus();

	// 设置状态码
	public void setStatus(int status);

	// 获取响应消息
	public String getMessage();

	// 设置响应消息
	public void setMessage(String message);

	// 设置附件信息
	public void setDisposition(String disposition);
}
