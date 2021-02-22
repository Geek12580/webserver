package com.webserver;

import java.util.Map;

/**
 * 封装http请求接口
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年7月3日
 */
public interface Request {
	// 设置请求协议
	public void setProtocol(String protocol);

	// 设置请求uri
	public void setUri(String uri);

	// 获取请求协议
	public String getProtocol();

	// 获取请求uri
	public String getUri();

	// 获取请求方法
	public String getRequestMethod();

	// 根据头部map的键取得对应的值
	public String getHeader(String key);

	// 根据参数map的键取得对应的值
	public String getParameters(String key);

	// 获取请求参数键值对集合
	public Map<String, String> getParameters();

	// 设置请求方法
	public void setRequestMethod(String requestMethod);

	// 获取请求头部键值对集合
	public Map<String, String> getHeader();
}
