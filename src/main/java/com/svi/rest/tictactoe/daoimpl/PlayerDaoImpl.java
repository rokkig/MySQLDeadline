package com.svi.rest.tictactoe.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import com.svi.rest.tictactoe.config.AppConfig;
import com.svi.rest.tictactoe.constants.PlayerQueries;
import com.svi.rest.tictactoe.dao.PlayerDao;
import com.svi.rest.tictactoe.dto.GameDetailsListDTO;
import com.svi.rest.tictactoe.dto.InfoDTO;
import com.svi.rest.tictactoe.dto.MessageDTO;
import com.svi.rest.tictactoe.dto.PlayersGamesListDTO;
import com.svi.rest.tictactoe.exceptions.RecordNotFoundException;
import com.svi.rest.tictactoe.exceptions.RecordNotSavedException;
import com.svi.rest.tictactoe.objects.PlayersGame;

public class PlayerDaoImpl implements PlayerDao{
	private Connection conn;
		
	private void openConn() {
		String endpoint = AppConfig.ENDPOINT.value();
		String username = AppConfig.USERNAME.value();
		String password = AppConfig.PASSWORD.value();
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(endpoint, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void closeConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public MessageDTO save(InfoDTO info) {
		// TODO Auto-generated method stub
		openConn();
		
		MessageDTO messageDTO = new MessageDTO();
		
		String gameId = info.getGameId();
		String playerId = info.getPlayerId();
		String symbol = info.getSymbol();
		Integer location = info.getLocation();
		String datesaved = info.getDatesaved();
		
		try {
			if (gameId != null && playerId != null && symbol != null && location != null && datesaved != null) {
			PreparedStatement insertPS = conn.prepareStatement(PlayerQueries.SAVE_STMNT.value());
			insertPS.setString(1, gameId);
			insertPS.setString(2, playerId);
			insertPS.setInt(3, location);
			insertPS.setString(4, symbol);
			insertPS.setString(5, datesaved);
			insertPS.executeUpdate();
			messageDTO.setMsg("Record saved.");
			} else {
				throw new RecordNotSavedException("Record could not be saved.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RecordNotSavedException("Record could not be saved.");
		} finally {
			closeConn();
		}
		
		return messageDTO;
	}

	@Override
	public GameDetailsListDTO getGames(String gameId) {
		// TODO Auto-generated method stub
		openConn();
		
		GameDetailsListDTO gameDetailsListDTO = new GameDetailsListDTO();
		List<InfoDTO> games = new ArrayList<>();
		
		try {		
			PreparedStatement getByGameIdPS = conn.prepareStatement(PlayerQueries.GETGAME_STMNT.value());
			getByGameIdPS.setString(1, gameId);
			
			ResultSet rs = getByGameIdPS.executeQuery();
			
			while(rs.next()) {				
				InfoDTO infoDTO = new InfoDTO();
				infoDTO.setGameId(rs.getString("gameid"));
				infoDTO.setPlayerId(rs.getString("playerid"));
				infoDTO.setLocation(rs.getInt("location"));
				infoDTO.setSymbol(rs.getString("symbol"));
				infoDTO.setDatesaved(rs.getString("recorddate"));
				games.add(infoDTO);	
			}
			
			gameDetailsListDTO.setList(games);
			
			if (games.isEmpty()) {
				throw new RecordNotFoundException("Record not found");
			} 			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new InternalServerErrorException();
		} finally {
			closeConn();
		}
		
		return gameDetailsListDTO;
	}

	@Override
	public PlayersGamesListDTO listGames(String playerId) {
		// TODO Auto-generated method stub
		openConn();
		PlayersGamesListDTO playersGamesListDTO = new PlayersGamesListDTO();
		List<PlayersGame> games = new ArrayList<>();
		
		try {			
			PreparedStatement getByPlayerIdPS = conn.prepareStatement(PlayerQueries.LISTGAMES_STMNT.value());
			getByPlayerIdPS.setString(1, playerId);	
			
			ResultSet rs = getByPlayerIdPS.executeQuery();
			
			while(rs.next()) {			
				PlayersGame playersGame = new PlayersGame(rs.getString("gameid"));
				games.add(playersGame);	
			}
			
			playersGamesListDTO.setList(games);
			
			if (games.isEmpty()) {
				throw new RecordNotFoundException("Record not found");
			} 		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new InternalServerErrorException();
		} finally {
			closeConn();
		}
		
		return playersGamesListDTO;
	}
}
