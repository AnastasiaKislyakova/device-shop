package ru.kislyakova.anastasia.shop.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonProcessingExceptionHandler implements ExceptionMapper<JsonProcessingException> {
    @Override
    public Response toResponse(JsonProcessingException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(JSONErrorMessage.create("invalid json: ", exception.getOriginalMessage()))
                .build();
    }
}