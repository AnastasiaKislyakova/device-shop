package ru.kislyakova.anastasia.shop.utils;

import ru.kislyakova.anastasia.shop.controller.rest.JSONErrorMessage;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Query validator class
 */
public class QueryValidator {

    /**
     *Checks request if it's empty
     *If received object is null method throws Bad Request
     *
     * @param o checked object
     * @throws WebApplicationException exception to be returned to client
     */
    public static void checkEmptyRequest(Object o) {
        if (o == null) {
            Response response = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(JSONErrorMessage.create("validation", "Request body is empty"))
                    .build();
            throw new WebApplicationException(response);
        }
    }

    /**
     * Checks if seeked object isn't found
     * If received object is null method throws Not Found
     *
     * @param o checked object
     * @param message message that is returned to client with response
     * @throws WebApplicationException exception to be returned to client
     */
    public static void checkIfNotFound(Object o, String message) {
        if (o == null) {
            Response response = Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(JSONErrorMessage.create("data", message))
                    .build();
            throw new WebApplicationException(response);
        }
    }
}
