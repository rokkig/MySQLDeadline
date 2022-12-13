package com.svi.rest.tictactoe.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.filechooser.FileSystemView;

import com.svi.rest.tictactoe.config.AppConfig;
import com.svi.rest.tictactoe.dto.InfoDTO;
import com.svi.rest.tictactoe.dto.MessageDTO;
import com.svi.rest.tictactoe.exceptions.RecordNotSavedException;

public class PostService {
	
	MessageDTO messageDTO = new MessageDTO();

	public MessageDTO saveInfo(InfoDTO body) {
		
		String gameId = body.getGameId();
		String playerId = body.getPlayerId();
		String symbol = body.getSymbol();
		Integer location = body.getLocation();
		String datesaved = body.getDatesaved();
		
		if (gameId != null && playerId != null && symbol != null && location != null && datesaved != null) {
			writeToPlayerFile(playerId, gameId);
			writeToGameFile(gameId, playerId, symbol, location, datesaved);
		} else {			
			throw new RecordNotSavedException("Record could not be saved.");
		}
		
		messageDTO.setMsg("Record saved.");
		return messageDTO;
		
	}
	
	private void writeToPlayerFile(String playerId, String gameId) {
		
		String homeDir = getFolderDirectory();
		String playersFolder = AppConfig.PLAYERSFOLDER.value();
		String playersFolderPath = homeDir + playersFolder;
		
		File outputFolder = new File(playersFolderPath); 
		if (!outputFolder.exists()) {
			outputFolder.mkdirs();
		}
						
		String playerFile = playersFolderPath + playerId + ".txt";
		File file = new File(playerFile);

		try {			
			if(file.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = br.readLine();
				boolean isGameIdWritten = false;
				
				while (line != null && !isGameIdWritten) {

					if (line.equals(gameId)) {		
						isGameIdWritten = true;
					} 
					line = br.readLine();						
				}
				
				if (!isGameIdWritten) {
					FileWriter fw = new FileWriter(playerFile, true);
					fw.write("\n"+gameId);
					fw.close();
				}	
				
				br.close();				
			} else {
				file.createNewFile();
				FileWriter fw = new FileWriter(playerFile);
				fw.write(gameId);
				fw.close();
			}
		} catch(Exception e) {
			e.getStackTrace();	   
		}    
	}
	
	private void writeToGameFile(String gameId, String playerId, String symbol, Integer location, String datesaved) {
		
		String homeDir = getFolderDirectory();
		String gamesFolder = AppConfig.GAMESFOLDER.value();
		String gamesFolderPath = homeDir + gamesFolder;
		
		File outputFolder = new File(gamesFolderPath); 
		outputFolder.mkdirs();
				
		String gameFile = gamesFolderPath + gameId + ".txt";
		File file = new File(gameFile);
		
		try {			
			if(file.exists()) {
				FileWriter fw = new FileWriter(gameFile, true);
				String str = String.format(",%s,%s,%s,%d,%s", gameId, playerId, symbol, location, datesaved);
				fw.write(str);
				fw.close();
			} else {
				file.createNewFile();
				String str = String.format("%s,%s,%s,%d,%s", gameId, playerId, symbol, location, datesaved);
				FileWriter fw = new FileWriter(gameFile);
				fw.write(str);
				fw.close();
			}
		} catch(Exception e) {
			e.getStackTrace();
	    }    
		
	}
	
	private String getFolderDirectory() {
		FileSystemView filesys = FileSystemView.getFileSystemView();
		String homeDir = filesys.getHomeDirectory().toString();
		return homeDir;
	}
	
}
