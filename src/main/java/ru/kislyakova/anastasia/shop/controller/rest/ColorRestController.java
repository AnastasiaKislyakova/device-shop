package ru.kislyakova.anastasia.shop.controller.rest;

import ru.kislyakova.anastasia.shop.controller.rest.handlers.ColorWebHandler;
import ru.kislyakova.anastasia.shop.model.Color;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("color")
public class ColorRestController {
    private ColorWebHandler colorWebHandler;

    @Inject
    public ColorRestController(ColorWebHandler colorWebHandler) {
        this.colorWebHandler = colorWebHandler;
    }

    @GET
    @Path("{rgb}")
    @Produces(MediaType.APPLICATION_JSON)
    public Color getColor(@PathParam("rgb") int rgb) {
        Color color = colorWebHandler.getColor(rgb);
        return color;
    }
}
