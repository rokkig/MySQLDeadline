package com.svi.rest.tictactoe.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RecordNotSavedExceptionMapper implements ExceptionMapper<RecordNotSavedException> {

	@Override
	public Response toResponse(RecordNotSavedException exception) {	
		return Response.status(Status.UNAUTHORIZED).entity(exception.getMessage()).build();
	}

}
