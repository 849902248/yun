package com.km.pojo;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Request {

	// 输入流
	private InputStream input;
	private String uri;
	private String action;

	public Request(InputStream input, String uri, String action) {
		super();
		this.input = input;
		this.uri = uri;
		this.action = action;
	}
	

	public Request() {
		super();
	}


	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Request(InputStream inputStream) {
		this.input = inputStream;
	}

	public InputStream getInput() {
		return input;
	}

	public void setInput(InputStream input) {
		this.input = input;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
	@Override
	public String toString() {
		return "Request [input=" + input + ", uri=" + uri + ", action=" + action + "]";
	}


	// 从套接字中读取字符信息
	public void parse() {

		StringBuffer request = new StringBuffer(2048);
		int i = 0;
		byte[] buffer = new byte[2048];

		try {
			i = input.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			i = -1;
		}
		for (int j = 0; j < i; j++) {
			request.append((char) (buffer[j]));
		}
		System.out.println("request+" + request.toString());
		uri = parseUri(request.toString());
		System.out.println("uri+" + uri);
		// 判断请求的操作
		// 打开exe
		if (uri.indexOf("/cmd_getFile") != -1) {
			action = "GETFILE";
		}
		// 生成二维码
		if (uri.indexOf("/cmd_genQRCode") != -1) {
			action = "GENQRCODE";
		} else {
			action="NOT_A_COMMAND";
		}
	}

	// 截取请求的url
	private String parseUri(String requestString) {

		int index1 = 0;
		int index2 = 0;
		index1 = requestString.indexOf(' ');
		if (index1 != -1) {
			index2 = requestString.indexOf(' ', index1 + 1);
			if (index2 > index1) {
				return requestString.substring(index1 + 1, index2);
			}
		}
		return null;
	}
}