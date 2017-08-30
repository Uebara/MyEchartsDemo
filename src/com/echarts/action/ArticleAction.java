package com.echarts.action;

import com.opensymphony.xwork2.ActionSupport;

public class ArticleAction extends ActionSupport {
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;
	private String name;
	private String color;
	private String size;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String addType() {
		System.out.println(name+color+size);

		result = "ÄãºÃ°¡¶÷";
		return SUCCESS;
	}
}
