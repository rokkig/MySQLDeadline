package com.svi.rest.tictactoe.constants;

public enum PlayerQueries {
	SAVE_STMNT("INSERT INTO 2022ptbatch02.records_hgaray(gameid, playerid, location, symbol, recorddate) VALUES(?,?,?,?,?)"),
	LISTGAMES_STMNT("SELECT DISTINCT gameid FROM 2022ptbatch02.records_hgaray WHERE playerid=?"),
	GETGAME_STMNT("SELECT * FROM 2022ptbatch02.records_hgaray WHERE gameid=?");
	
	private String value;
	
	private PlayerQueries(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
