package ru.kislyakova.anastasia.shop.controller.rest.handlers;

import ru.kislyakova.anastasia.shop.service.ColorService;
import ru.kislyakova.anastasia.shop.model.Color;

import javax.inject.Inject;

public class ColorWebHandler {
    private ColorService colorService;

    @Inject
    public ColorWebHandler(ColorService colorService) {
        this.colorService = colorService;
    }

    public Color getColor(int rgb){
        return colorService.getColorByRGB(rgb);
    }
}
