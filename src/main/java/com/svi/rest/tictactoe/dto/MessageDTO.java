package com.svi.rest.tictactoe.dto;

import javax.xml.bind.annotation.XmlElement;

public class MessageDTO {
	
	@XmlElement
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}	
	
}
