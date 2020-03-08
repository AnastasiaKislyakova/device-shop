package ru.kislyakova.anastasia.shop.utils;

import ru.kislyakova.anastasia.shop.controller.rest.JSONErrorMessage;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * Model validator class
 */
public class ModelValidator{

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = validatorFactory.getValidator();

    /**
     * Checks received entity according its annotations
     * Catches the first mismatch of some entity's property and its annotation and throws Bad Request
     *
     * @param entity checked entity
     * @param <T> type of checked entity
     * @throws WebApplicationException exception to be returned to client
     */
    public static <T> void validateEntity(T entity){
        Set<ConstraintViolation<T>> validate = validator.validate(entity);
        for(ConstraintViolation<T> violation : validate){
            Response response = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(JSONErrorMessage.create("validation", violation.getMessage()))
                    .build();
            throw new WebApplicationException(response);
        }
    }
}
