package com.webserver;

/**
 * 封装成http形式的请求的具体实现类
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年6月26日
 */
import java.util.LinkedHashMap;
import java.util.Map;

public class WebRequest implements Request {
	// 协议
	private String protocol;
	// 请求uri
	private String uri;
	// 请求方法 get还是post
	private String requestMethod;
	// 头部信息的键值对存储
	private Map<String, String> header = new LinkedHashMap<String, String>();
	// 请求的参数
	private Map<String, String> parameters = new LinkedHashMap<String, String>();

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getUri() {
		return uri;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	/*
	 * 根据头部map的键取得对应的值
	 */
	public String getHeader(String key) {
		return header.get(key);
	}

	/*
	 * 根据参数map的键取得对应的值
	 */
	public String getParameters(String key) {
		return parameters.get(key);
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public Map<String, String> getHeader() {
		return header;
	}
}
