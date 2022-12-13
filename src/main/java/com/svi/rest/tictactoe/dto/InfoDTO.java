package com.svi.rest.tictactoe.dto;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InfoDTO {
	
	@XmlElement
	private String gameId;	@XmlElement
	private String playerId;
	@XmlElement
	private String symbol;
	@XmlElement
	private int location;
	@XmlElement
	private String datesaved;
	
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public String getDatesaved() {
		return datesaved;
	}
	public void setDatesaved(String datesaved) {
		this.datesaved = datesaved;
	}
	
	@Override
	public String toString() {
		String json = "";
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			json = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
}
