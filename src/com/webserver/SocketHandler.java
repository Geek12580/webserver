package com.webserver;
/**
 * 具体请求的调度中心
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年6月28日
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketHandler implements Runnable {
	private Socket socket;
	private static final Logger log = LoggerFactory.getLogger(SocketHandler.class);

	public SocketHandler(Socket socket) {
		super();
		this.socket = socket;
	}

	/**
	 * 处理连接请求，封装成request，再通过uri判断相关的请求类型 
	 * 将处理结果封装成response 在response中返回
	 * @throws IOException 
	 */
	public void handleRequest(){
		try {
			// 通过socket绑定输入流
			InputStream is = socket.getInputStream();
			try {
				// 通过socket绑定输出流
				OutputStream os = socket.getOutputStream();
				try {
					// 封装request
					RequestDispatch reqDis = new RequestDispatch(is);
					Request request = reqDis.getRequest();
					// 封装response
					ResponseDispatch rspDis = new ResponseDispatch(request);
					Response response = rspDis.getResponse();
					// 结果打印类处理最后的返回工作
					new ResultPrint(os, response);
				} finally {
					os.close();
				}

			} finally {
				is.close();
			}

		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run(){
		log.info(Thread.currentThread().getName() + "正在处理http请求");
		handleRequest();
	}
}
