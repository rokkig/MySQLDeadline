package com.svi.rest.tictactoe.dao;

import java.util.List;

import com.svi.rest.tictactoe.dto.GameDetailsListDTO;
import com.svi.rest.tictactoe.dto.InfoDTO;
import com.svi.rest.tictactoe.dto.MessageDTO;
import com.svi.rest.tictactoe.dto.PlayersGamesListDTO;

public interface PlayerDao {
	public MessageDTO save(InfoDTO info);
	public PlayersGamesListDTO listGames(String playerId);
	public GameDetailsListDTO getGames(String gameId);
}
