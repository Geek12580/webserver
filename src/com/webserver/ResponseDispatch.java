package com.webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.utils.Coding;
import com.utils.FileOperation;
import com.utils.StatusCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * response的业务类 负责将响应封装成response
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年7月3日
 */
public class ResponseDispatch {
	// 获取请求封装的request，获得协议版本信息等
	private Request request;
	private Response response;
	private static final Logger log = LoggerFactory.getLogger(ResponseDispatch.class);
	// 获取当前项目的根路径
	private static final String root = System.getProperty("user.dir");

	public ResponseDispatch(Request request) throws IOException {
		this.request = request;
		response = new WebResponse();
		loadResponse();
	}

	public Response getResponse() {
		return response;
	}

	/*
	 * 读取响应的信息，保存信息到实体类相关的成员变量中
	 */
	private void loadResponse() throws IOException {
		// 访问的uri地址
		String uri = request.getUri();
		// 保存响应内容
		byte[] responseContent = null;
		// 针对粘包的情况或者是异常的情况，选择放弃这次请求
		if (null == uri) {
			return;
		}
		// 获取项目名称
		String projectName = root.substring(root.lastIndexOf("\\") + 1, root.length());
		// 相对uri为空字符串或者是项目名称，默认访问index.jsp
		if (("").equals(uri) || projectName.equals(uri)) {
			uri = "index.jsp";
		}

		/*
		 * 访问的类型包括以下几种
		 *  1.访问系统的根目录 
		 *  2.访问具体的文件 
		 *  3.保存文件 
		 *  4.下载文件 
		 *  5.查看文件
		 *  6.直接通过url访问文件
		 */
		// 访问根目录
		if (uri.contains("callRoot.do")) {
			String URL = root;
			responseContent = writeFolder(URL);
		}
		// 访问具体的文件
		else if (uri.contains("callFile.do")) {
			String URL = root + "/" + uri.replaceAll("callFile.do", "");
			responseContent = writeFile(URL, "");
		}
		// 保存文件
		else if (uri.contains("saveFile.do")) {
			// 用文件的工具类处理文件的保存
			String path = request.getParameters("path");
			String absolutePath = root + "/" + path;
			absolutePath = absolutePath.replaceAll("//", "/");
			String content = request.getParameters("content");
			new FileOperation().saveFile(absolutePath, content);
			appendHeader(true, "");
			try {
				responseContent = "保存成功".getBytes(Coding.DEFALUT_CODING);
			} catch (UnsupportedEncodingException e) {
				log.error("不支持的编码", e);
				throw e;
			}
		}
		// 下载文件
		else if (uri.contains("downloadFile.do")) {
			String fileName = uri.replaceAll("downloadFile.do", "");
			// 拼接待下载文件的绝对路径
			String URL = root + "/" + fileName;
			// 取得待下载文件的名称
			fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
			// 下载文件添加特殊的http响应头内容
			String disposition = "Content-Disposition: attachment; filename=" + fileName;
			responseContent = writeFile(URL, disposition);
		}
		// 查看文件
		else if (uri.contains("openFile.do")) {
			uri = uri.replaceAll("openFile.do", "");
			String URL = root + "/" + uri;
			responseContent = writeFile(URL, "");
		}
		// 直接通过url访问文件，文件的位置为webroot目录
		else {
			uri = uri.replaceAll("webroot", "");
			String URL = root + "/webroot/" + uri;
			responseContent = writeFile(URL, "");
		}

		// 设置响应的正文
		response.setResponseContent(responseContent);
	}

	/**
	 * 添加响应头部
	 * @param isFound 表示资源是否找到
	 * @param download 下载文件的头部信息
	 * @throws IOException 
	 */
	public void appendHeader(boolean isFound, String download) throws IOException {
		response.setProtocol(request.getProtocol());
		response.setDisposition(download);
		// 未找到文件，响应头部拼接404HTTP状态码
		if (!isFound) {
			response.setMessage("Not Found");
			response.setStatus(StatusCode.SC_NOT_FOUND);
		} else {
			response.setMessage("OK");
			response.setStatus(StatusCode.SC_OK);
		}
		// 使用通配符匹配所有的请求返回类型，统一编码为utf-8
		try {
			response.setHeader();
		} catch (UnsupportedEncodingException e) {
			log.error("不支持的编码!", e);
			throw e;
		}
	}

	/*
	 * 访问文件
	 */
	public byte[] writeFile(String uri, String download) throws IOException {
		uri = uri.replaceAll("//", "/");
		File file = new File(uri);
		byte[] fileBytes = null;
		// 文件不存在或找不到提示404
		if (!file.exists() || file == null) {
			appendHeader(false, "");
			String content = "<html><body><center><h1>HTTP Status 404 -" + uri + "</h1></center></body></html>";
			try {
				return content.getBytes(Coding.DEFALUT_CODING);
			} catch (UnsupportedEncodingException e) {
				log.error("不支持的编码", e);
				throw e;
			}
		} else {
			int length = (int) file.length();
			fileBytes = new byte[length];
			FileInputStream fis = null;
			try {
				// 获取到文件的字节流
				fis = new FileInputStream(file);
				appendHeader(true, download);
				// 记录当前字节位置
				int cur = 0;
				while (cur < length) {
					cur += fis.read(fileBytes, cur, length - cur);
				}
			} catch (FileNotFoundException e) {
				log.error("文件未找到！", e);
				throw new IOException("文件未找到！"+"uri",e);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				throw e;
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						log.error("打开文件输入流关闭失败！", e);
						throw e;
					}
				}
			}
		}
		return fileBytes;
	}

	/**
	 * 请求为文件夹 返回给浏览器指定文件夹下的所有文件列表
	 * @param uri 传入的uri地址 例如files?path=D:/succezIDE/test
	 * @throws IOException 
	 */
	public byte[] writeFolder(String uri) throws IOException {
		// 截取传入uri地址中文件的绝对路径
		String absolutePath = uri.substring(uri.indexOf("=") + 1, uri.length());
		JSONArray jsonArray = new JSONArray();
		jsonArray = traverseFile(absolutePath);
		String content = jsonArray.toString();
		byte[] folderBytes = null;
		// 添加头部信息
		appendHeader(true, "");
		try {
			folderBytes = content.getBytes(Coding.DEFALUT_CODING);
		} catch (UnsupportedEncodingException e) {
			log.error("不支持的编码", e);
			throw e;
		}
		return folderBytes;
	}

	/**
	 * 递归遍历文件夹，返回当前路径下所有文件
	 * @param uri 路径
	 * @param jsonArray 保存的文件信息的json数组
	 * @return
	 */
	private JSONArray traverseFile(String absolutePath) {
		// 获取文件列表
		File[] fileList = new File(absolutePath).listFiles();
		// 存贮文件
		JSONArray fileArray = new JSONArray();
		// 存储文件夹
		JSONArray folderArray = new JSONArray();
		for (File file : fileList) {
			JSONObject job = new JSONObject();
			job.put("name", file.getName());
			job.put("size", file.length());
			job.put("lastModified", file.lastModified());
			job.put("folder", file.isDirectory());
			if (file.isDirectory()) {
				folderArray.add(job);
				JSONArray jsonArray = traverseFile(absolutePath + "/" + file.getName());
				folderArray.add(jsonArray);
				continue;
			}
			fileArray.add(job);
		}
		// 将文件添加到文件夹的后面
		folderArray.addAll(fileArray);
		return folderArray;
	}
}
