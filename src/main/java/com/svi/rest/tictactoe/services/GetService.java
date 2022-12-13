package com.svi.rest.tictactoe.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.filechooser.FileSystemView;

import com.svi.rest.tictactoe.config.AppConfig;
import com.svi.rest.tictactoe.dto.GameDetailsListDTO;
import com.svi.rest.tictactoe.dto.InfoDTO;
import com.svi.rest.tictactoe.dto.PlayersGamesListDTO;
import com.svi.rest.tictactoe.exceptions.RecordNotFoundException;
import com.svi.rest.tictactoe.objects.PlayersGame;

public class GetService {
	
	FileSystemView filesys = FileSystemView.getFileSystemView();
	String homeDir = filesys.getHomeDirectory().toString();
		
	public PlayersGamesListDTO getPlayersGamesList(String playerId) throws IOException {
		
		String playersFolder = AppConfig.PLAYERSFOLDER.value();
		String fileName = playerId + ".txt";
		String playersFolderPath = homeDir + playersFolder +  fileName;
		
		PlayersGamesListDTO playersGamesListDTO = new PlayersGamesListDTO();
		
		File file = new File(playersFolderPath);
		
		if (file.exists()) {
			FileReader fr = new FileReader(file);
			
			BufferedReader br = new BufferedReader(fr);
			List<PlayersGame> playersGamesList = new ArrayList<>();
			String gameId = br.readLine();
			
			while (gameId != null) {	
				PlayersGame playersGame = new PlayersGame(gameId);
				playersGamesList.add(playersGame);
				gameId = br.readLine();
			}
			playersGamesListDTO.setList(playersGamesList);
			fr.close();
			br.close();		
		} else {
			throw new RecordNotFoundException("Record not found");
		}
		
		return playersGamesListDTO;
	}
	
	public GameDetailsListDTO getGameDetails(String gameId) throws IOException {

		String gamesFolder = AppConfig.GAMESFOLDER.value();
		String fileName = gameId + ".txt";
		String gamesFolderPath = homeDir + gamesFolder +  fileName;
		
		File file = new File(gamesFolderPath);
		GameDetailsListDTO gamesListDTO = new GameDetailsListDTO();
		List<InfoDTO> gamesList = new ArrayList<>();
		
		if (file.exists()) {
			FileReader fr = new FileReader(file);			
			BufferedReader br = new BufferedReader(fr);
			String input = br.readLine();
			
			StringTokenizer st = new StringTokenizer(input);
			
			while (st.hasMoreTokens()) {
				InfoDTO infoDTO = new InfoDTO();
				infoDTO.setGameId(st.nextToken(","));
				infoDTO.setPlayerId(st.nextToken(","));
				infoDTO.setSymbol(st.nextToken(","));
				infoDTO.setLocation(Integer.parseInt(st.nextToken(",")));
				infoDTO.setDatesaved(st.nextToken(","));
				gamesList.add(infoDTO);		
			}
			
			fr.close();
			br.close();		
		} else {
			throw new RecordNotFoundException("Record not found");
		}
		
		gamesListDTO.setList(gamesList);
		return gamesListDTO;
	}
	
}
