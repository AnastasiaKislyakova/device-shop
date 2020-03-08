package ru.kislyakova.anastasia.shop.controller.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(JSONErrorMessage.create("invalid request", exception.getMessage()))
                .build();
    }
}