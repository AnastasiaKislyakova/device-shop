package ru.kislyakova.anastasia.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kislyakova.anastasia.shop.model.Color;
import ru.kislyakova.anastasia.shop.storage.filter.FilterConditional;
import ru.kislyakova.anastasia.shop.storage.filter.StorageFilter;
import ru.kislyakova.anastasia.shop.storage.Storage;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;

class ColorServiceImplTest {
    private Storage<Color> storage = (Storage<Color>) Mockito.mock(Storage.class);
    private ColorService colorService = new ColorServiceImpl(storage);

    @BeforeEach
    void setUp() {
        Mockito.reset(storage);
    }

    @Test
    void should_correctly_save_color_and_set_id() {
        Color color = new Color("новый", 12);

        Color saved = colorService.saveColor(color);

        assertNotEquals(0, saved.getId());

        Mockito.verify(storage).save(eq(saved));
    }

    @Test
    void should_return_color_by_name() {
        String name = "новый";
        Color color = new Color(name, 12);

        Color saved = colorService.saveColor(color);
        List<Color> colors = Collections.singletonList(saved);
        Mockito.when(storage.get(anyObject())).thenReturn(colors);

        Color actual = colorService.getColorByName(saved.getName());

        StorageFilter<Color> storageFilter = new StorageFilter<>();
        storageFilter.addCondition(FilterConditional.on(Color::getName).eq(name));

        assertEquals(colors.get(0), actual);
        Mockito.verify(storage).get(eq(storageFilter));
    }

    @Test
    void should_return_color_by_rgb() {
        String name = "новый";
        int rgb = 12;
        Color color = new Color(name, rgb);

        Color saved = colorService.saveColor(color);
        List<Color> colors = Collections.singletonList(saved);
        Mockito.when(storage.get(anyObject())).thenReturn(colors);

        Color actual = colorService.getColorByRGB(saved.getRgb());

        StorageFilter<Color> storageFilter = new StorageFilter<>();
        storageFilter.addCondition(FilterConditional.on(Color::getRgb).eq(rgb));

        assertEquals(colors.get(0), actual);
        Mockito.verify(storage).get(eq(storageFilter));
    }



}