package com.svi.rest.tictactoe.init;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.svi.rest.tictactoe.config.AppConfig;

@WebListener
public class ContextInitializer implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		ServletContext sc = sce.getServletContext();
		String path = sc.getInitParameter("CONFIG_LOCATION");
		String absPath = sc.getRealPath(path);
		
		try {
			AppConfig.initializeConfig(new FileInputStream(absPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
