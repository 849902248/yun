package com.km.pojo;

public class QRCodeKM {
	public String content="www.baidu.com";
	public Integer height=400;
	public Integer width=400;
	public String Logo="C:\\Users\\84990\\Desktop\\test\\computer.ico";
	public String text="跳转到百度的二维码";

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public String getLogo() {
		return Logo;
	}

	public void setLogo(String logo) {
		Logo = logo;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public QRCodeKM(String content, Integer height, Integer width, String logo, String text) {
		super();
		this.content = content;
		this.height = height;
		this.width = width;
		Logo = logo;
		this.text = text;
	}

	public QRCodeKM() {
		super();
	}

	@Override
	public String toString() {
		return "QRCodeKM [content=" + content + ", height=" + height + ", width=" + width + ", Logo=" + Logo + ", text="
				+ text + "]";
	}

}
