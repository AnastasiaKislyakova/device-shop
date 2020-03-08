package ru.kislyakova.anastasia.shop.service;

import ru.kislyakova.anastasia.shop.model.Color;

public interface ColorService {
    Color saveColor(Color color);

    Color getColorByName(String name);

    Color getColorByRGB(int rgb);

}
