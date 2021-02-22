package com.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 连接器，负责接受网页的请求并分配一个线程进行处理
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年6月26日
 */
public class Connector {
	private static final Logger log = LoggerFactory.getLogger(Connector.class);
	private static final int port = 8080;
	// 定义一个固定大小的线程池，设置线程数为15
	private ExecutorService executor = Executors.newFixedThreadPool(15);
	// 服务端套接字，绑定服务器的端口
	private ServerSocket server;

	public static void main(String[] args) throws IOException {
		new Connector().acceptRequest();
	}

	/**
	 * 接收客户端请求
	 * @throws IOException 
	 */
	public void acceptRequest() throws IOException {
		try {
			// 监听8080端口
			server = new ServerSocket(port);
			log.info("服务器已启动，监听端口号为 " + port);
		} catch (IOException e) {
			log.error("服务器启动失败：", e);
			throw e;
		}
		// 获取连接到的客户端
		while (true) {
			try {
				Socket socket = server.accept();
				String ip = socket.getInetAddress().getHostAddress();
				log.info("连接到客户端的ip地址为" + ip);
				// 在线程池中分配线程处理该请求
				executor.execute(new SocketHandler(socket));
			} catch (IOException e) {
				log.error("连接客户端发生异常", e);
				throw e;
			}
		}
	}
}