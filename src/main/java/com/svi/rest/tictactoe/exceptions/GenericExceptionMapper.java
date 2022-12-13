package com.svi.rest.tictactoe.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		String message = "The server ran into an unexpected exception.";
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(message).build();
	}

}

