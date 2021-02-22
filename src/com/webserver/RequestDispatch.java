package com.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.utils.Coding;

/**
 * request的业务类 负责将请求封装成request
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年7月3日
 */
public class RequestDispatch {
	// 接受请求的输入流
	private InputStream is;
	private static final Logger log = LoggerFactory.getLogger(RequestDispatch.class);
	// 实体类请求类
	Request request;

	public RequestDispatch(InputStream is) throws IOException {
		this.is = is;
		request = new WebRequest();
		loadRequest();
	}

	public Request getRequest() {
		return request;
	}

	/**
	 * 读取html请求信息，保存信息到实体类相关的成员变量中
	 * @throws IOException 
	 */
	public void loadRequest() throws IOException {
		try {
			// html请求没有明显的结束标志，这里采用字符流，每次读取一行
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Coding.DEFALUT_CODING));
			String line = br.readLine();
			// 对第一行特殊处理，包含协议、方法等
			setRequestInfo(line);
			while (!("".equals(line = br.readLine()))) {
				setRequestHeader(line);
			}
			/*
			 * 处理完请求头的部分，如果是post请求，则还会有正文部分 接着处理正文的部分
			 * post请求定义了contentLength，不需要按行读取
			 */
			setRequestBody(br);
		} catch (UnsupportedEncodingException e) {
			log.error("不支持的字符编码类型", e);
			throw e;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	/*
	 * 保存请求头部中第一行的相关信息， 包括请求方法，uri，get请求参数，协议等
	 *  例如GET /ExerciseOne.java?para=5 HTTP/1.1
	 */
	public void setRequestInfo(String line) throws IOException {
		if (line == null || line == "") {
			return;
		}
		// 设置请求方法
		String method = line.substring(0, line.indexOf(" "));
		request.setRequestMethod(method);
		// 设置相对请求uri
		int index1 = 0;
		int index2 = 0;
		String uri = null;
		index1 = line.indexOf(' ');
		if (index1 != -1) {
			index2 = line.indexOf(' ', index1 + 1);
			if (index2 > index1) {
				// 如果get请求？后没有带参数，去除？
				if (line.charAt(index2 - 1) == '?') {
					index2--;
				}
				uri = line.substring(index1 + 2, index2);
			}
		}
		request.setUri(uri);
		// 设置get请求的参数
		// /index.html?user=zsw&pass=123456
		if ("get".equalsIgnoreCase(method) && (uri.indexOf('?') != -1)) {
			// 截取问号之后的get参数字符串
			String paramString = uri.substring(uri.indexOf("?") + 1);
			// 将get参数保存到参数map中
			saveToParamsMap(paramString);
		}
		// 设置请求的协议
		String protocol = line.substring(index2 + 1);
		request.setProtocol(protocol);
	}

	/*
	 * 将请求参数保存到参数map中 包括get和post请求
	 */
	private void saveToParamsMap(String paramString) throws IOException {
		// 分割成若干个键值对
		String[] params = paramString.split("&");
		// 将键值对保存到请求参数map中
		Map<String, String> requestMap = request.getParameters();
		// 为了消除文本内容特殊字符对字符串截取的影响，在截取完成之后再对消息内容进行解码
		try {
			for (String param : params) {
				String[] keyValue = param.split("=");
				String key = URLDecoder.decode(keyValue[0], Coding.DEFALUT_CODING);
				String value = URLDecoder.decode(keyValue[1], Coding.DEFALUT_CODING);
				requestMap.put(key, value);
			}
		} catch (UnsupportedEncodingException e) {
			log.error("不支持的编码!", e);
			throw e;
		}
	}

	/**
	 * 处理post请求的主体部分 将post请求的参数保存到参数map中
	 * @param br 传入的字符流对象
	 * @throws IOException 
	 */
	public void setRequestBody(BufferedReader br) throws IOException {
		// 如果是post请求并且正文的长度不为0，处理下面的逻辑
		if ("post".equalsIgnoreCase(request.getRequestMethod())) {
			int length = Integer.parseInt(request.getHeader("Content-Length"));
			if (length != 0) {
				// 用字节数组读取post正文
				char[] bytes = new char[length];
				int cur = 0;
				while (cur < length) {
					try {
						cur += br.read(bytes, cur, length - cur);
					} catch (IOException e) {
						log.error(e.getMessage(), e);
						throw e;
					}
				}
				String requestBody = null;
				requestBody = new String(bytes, 0, length);
				saveToParamsMap(requestBody);
			}
		}
	}

	/**
	 * 按照行来保存请求头部 以键值对来存储
	 * @param line 读取的一行数据
	 */
	public void setRequestHeader(String line) {
		if (line == null) {
			return;
		}
		// 用:为界限，分别取出请求行的键和值
		int index = line.indexOf(":");
		String key = line.substring(0, index).trim();
		String value = line.substring(index + 1, line.length()).trim();
		request.getHeader().put(key, value);
	}
}
