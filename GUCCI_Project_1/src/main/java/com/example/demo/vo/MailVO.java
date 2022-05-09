package com.example.demo.vo;

public class MailVO {
	  
	  private String title;
	  private String message;
	  private String address;
	  
	public MailVO() {}
	
	public MailVO(String title, String message,String address) {
		super();
		this.message = message;
		this.title = title;
		this.address = address;
	}
	 

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	  
}
