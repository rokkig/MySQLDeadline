package com.svi.rest.tictactoe.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GameDetailsListDTO {
	
	private List<InfoDTO> list = new ArrayList<>();
	private String msg = "Records found";
	
	public List<InfoDTO> getList() {
		return list;
	}
	public void setList(List<InfoDTO> list) {
		this.list = list;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
