package com.svi.rest.tictactoe.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.svi.rest.tictactoe.objects.PlayersGame;

@XmlRootElement
public class PlayersGamesListDTO {

	private List<PlayersGame> list = new ArrayList<>();
	private String msg = "Records found";

	public void setList(List<PlayersGame> list) {
		this.list = list;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<PlayersGame> getList() {
		return list;
	}	
	
}
