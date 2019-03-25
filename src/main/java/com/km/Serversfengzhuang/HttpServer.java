package com.km.Serversfengzhuang;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.WriterException;
import com.km.pojo.Request;
import com.km.pojo.Response;
import com.km.util.QRCodeGen;

public class HttpServer {
	private static String imageBase64QRCode = null;
	// WEB_ROOT该服务器的根目录，这个目录可以自己定义，主要是服务器响应的文件所在目录
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

	public static void main(String[] args) {
		final HttpServer server = new HttpServer();
		final Request request = new Request();
		System.out.println("WEB_ROOT:" + WEB_ROOT);
		// 主线程,开启浏览器等待
		server.await();
		// 定义线程2,准备执行exe
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				InputStream inputStream = null;
				Request request = new Request(inputStream);
				String exeName = request.getUri().split("=")[1];
				System.out.println("exeName:" + exeName);
				// 运行文件
				String[] cmd = { "C:\\Users\\84990\\Desktop\\test\\test\\" + exeName };
				Process process = null;
				try {
					Runtime runtime = Runtime.getRuntime();
					process = runtime.exec(cmd);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// 定义线程3,生成二维码
		Thread thread3 = new Thread(new Runnable() {
			public void run() {
				// 生成二维码
				String qRparamsStr = request.getUri().split("[?]")[1];
				String[] qRparams = qRparamsStr.split("&");
				Map<String, String> qRCodeMap = new HashMap<String, String>();
				for (int j = 0; j < qRparams.length; j++) {
					String qRParam[] = qRparams[j].split("=");
					String qRKey = qRParam[0];
					String qRValue = qRParam[1];
					qRCodeMap.put(qRKey, qRValue);
				}
				System.out.println("qRCodeMap" + qRCodeMap.toString());

				// 生成二维码
				QRCodeGen qRCodeGen = new QRCodeGen();
				try {
					// 返回一个base64字符，响应给网页
//					imageBase64QRCode = qRCodeGen.generateQR(qRCodeMap);
//					System.out.println("imageBase64QRCode:" + imageBase64QRCode);
					//直接返给浏览器流更方便
					ByteArrayOutputStream baos=qRCodeGen.generateQR(qRCodeMap);	
					byte[] byteArr=baos.toByteArray();
					request.setInput(byteArr);
					java.io.OutputStream.write(byteArr, 1024, 1024);
				} catch (WriterException e) {
					e.printStackTrace();
				}
			}
		});
		// 开启运行文件的线程
		if ("GETFILE".equals(request.getAction())) {
			thread2.start();
		}
		// 开启生成二维码的线程
		if ("GETFILE".equals(request.getAction())) {
			thread3.start();
		}
	}

	public void await() {
		ServerSocket serverSocket = null;
		int port = 10003;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("localhost"));
			System.out.println("等待来自浏览器的连接...");
			while (true) {
				try {
					Socket socket = null;
					InputStream input = null;
					OutputStream output = null;
					socket = serverSocket.accept();
					input = socket.getInputStream();
					output = socket.getOutputStream();
					// 封装request请求
					Request request = new Request(input);
					request.parse();
					// 封装response对象
					Response response = new Response(output);
					response.setRequest(request);
					response.sendStaticResource();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}