package com.webserver;
/**
 * 封装http响应的实现类
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年6月26日
 */

import java.io.UnsupportedEncodingException;

import com.utils.Coding;

public class WebResponse implements Response {
	// 响应的状态码 ，如200，404
	private int status;
	// 响应头的响应消息，如OK
	private String message;
	// http协议版本
	private String protocol;
	// 返回类型
	private final String contentType = "*/*";
	// 空格
	private final String BLANK = " ";
	// 响应头
	private byte[] header;
	// 换行符
	private final String CHANGE_LINE = "\r\n";
	// 响应正文
	private byte[] responseContent;
	// 响应附件内容
	private String disposition;

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public WebResponse() {
	}

	public byte[] getResponseContent() {
		return responseContent;
	}

	public void setResponseContent(byte[] responseContent) {
		this.responseContent = responseContent;
	}

	public String getContentType() {
		return contentType;
	}

	/*
	 * 拼接响应头部
	 */
	public void setHeader() throws UnsupportedEncodingException {
		String head = protocol + BLANK + status + BLANK + message + CHANGE_LINE;
		head += "Content-Type:" + contentType + ";charset=" + Coding.DEFALUT_CODING + CHANGE_LINE;
		if (this.disposition != "") {
			head += this.disposition + CHANGE_LINE;
		}
		head += CHANGE_LINE;
		header = head.getBytes(Coding.DEFALUT_CODING);
	}

	public byte[] getHeader() {
		return header;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
