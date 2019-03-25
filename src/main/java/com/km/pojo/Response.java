package com.km.pojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import com.km.Serversfengzhuang.HttpServer;

public class Response {
	
	Request request;
	OutputStream output;
	public Response(OutputStream output){
		this.output = output;
	}
	
	public void sendStaticResource() throws IOException{
		
		
					output.write(bytes,0,ch);
				
			
		}else{
			//找不到文件
			  String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
	          "Content-Type: text/html\r\n" +
	          "Content-Length: 23\r\n" +
	          "\r\n" +
	          "<h1>File Not Found</h1>";
			  try {
				output.write(errorMessage.getBytes());
				output.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public OutputStream getOutput() {
		return output;
	}
	public void setOutput(OutputStream output) {
		this.output = output;
	}
}