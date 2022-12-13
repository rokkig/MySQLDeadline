package com.svi.rest.tictactoe.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum AppConfig {
	PLAYERSFOLDER("PLAYERSFOLDER"),
	GAMESFOLDER("GAMESFOLDER");
	
	private String value = "";
	private static Properties prop;
	
	private AppConfig(String value) {
		this.value = value;
	}
	
	public String value() {
		return prop.getProperty(value).trim();
	}
	
	public static void initializeConfig(InputStream is) {
		synchronized (is) {
			if(prop == null) {			
				try {
					prop = new Properties();
					prop.load(is);					
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
