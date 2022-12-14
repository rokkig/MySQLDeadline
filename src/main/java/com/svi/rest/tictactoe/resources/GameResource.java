package com.svi.rest.tictactoe.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.svi.rest.tictactoe.dao.PlayerDao;
import com.svi.rest.tictactoe.daoimpl.PlayerDaoImpl;
import com.svi.rest.tictactoe.dto.GameDetailsListDTO;
import com.svi.rest.tictactoe.dto.InfoDTO;
import com.svi.rest.tictactoe.dto.MessageDTO;
import com.svi.rest.tictactoe.dto.PlayersGamesListDTO;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {

	PlayerDao playerDaoImpl = new PlayerDaoImpl();
	
	@POST
	@Path("/save")
	public Response postGameInfo(InfoDTO body) {
		
		MessageDTO message = playerDaoImpl.save(body);		
		return Response.ok().entity(message).build();
		
	}
	
	@GET
	@Path("/listgames/{playerId}")
	public Response getPlayerGamesList(@PathParam("playerId") String playerId) throws IOException {
		
		PlayersGamesListDTO playersGamesListDTO = playerDaoImpl.listGames(playerId);
		return Response.ok().entity(playersGamesListDTO).build();
	}
	
	@GET
	@Path("/getgame/{gameId}")
	public Response getGame(@PathParam("gameId") String gameId) throws IOException {
	
		GameDetailsListDTO gamesList = playerDaoImpl.getGames(gameId);		
		return Response.ok().entity(gamesList).build();
	}
	
}
