package ru.kislyakova.anastasia.shop.service;

import ru.kislyakova.anastasia.shop.storage.filter.FilterConditional;
import ru.kislyakova.anastasia.shop.model.Color;
import ru.kislyakova.anastasia.shop.storage.Storage;
import ru.kislyakova.anastasia.shop.storage.filter.StorageFilter;
import ru.kislyakova.anastasia.shop.utils.Identifier;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ColorServiceImpl implements ColorService {
    private Storage<Color> storage;
    private Identifier identifier = new Identifier();

    public ColorServiceImpl(Storage<Color> storage) {
        this.storage = storage;
        init();
    }

    private void init(){
        saveColor(new Color("черный", 0));
        saveColor(new Color("серый", 8421504));
        saveColor(new Color("красный", 16711680));
        saveColor(new Color("золотистый", 16766720));
        saveColor(new Color("синий", 255));
        saveColor(new Color("cеребристый", 12632256));
        saveColor(new Color("белый", 16777215));
        saveColor(new Color("коричневый", 9849600));
        saveColor(new Color("оранжевый", 16753920));
        saveColor(new Color("бежевый", 16119260));
        saveColor(new Color("желтый", 16776960));
        saveColor(new Color("зеленый", 32768));
        saveColor(new Color("голубой", 4369151));
        saveColor(new Color("фиолетовый", 9109759));
        saveColor(new Color("розовый", 16519104));
    }

    @Override
    public Color saveColor(Color color) {
        long id = identifier.nextId();
        color.setId(id);
        storage.save(color);
        return color;
    }

    @Override
    public Color getColorByName(String name) {
        StorageFilter<Color> storageFilter = new StorageFilter<>();
        storageFilter.addCondition(FilterConditional.on(Color::getName).eq(name));
        List<Color> colors = storage.get(storageFilter);
        return colors.isEmpty() ? null : colors.get(0);
    }

    @Override
    public Color getColorByRGB(int rgb) {
        StorageFilter<Color> storageFilter = new StorageFilter<>();
        storageFilter.addCondition(FilterConditional.on(Color::getRgb).eq(rgb));
        List<Color> colors = storage.get(storageFilter);
        return colors.isEmpty() ? null : colors.get(0);
    }
}
