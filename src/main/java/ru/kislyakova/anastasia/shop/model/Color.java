package ru.kislyakova.anastasia.shop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.kislyakova.anastasia.shop.storage.Unique;

import java.util.Objects;

public class Color implements Unique, Comparable<Color> {
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String RGB_FIELD = "rgb";

    @JsonProperty(ID_FIELD)
    private long id;

    @JsonProperty(NAME_FIELD)
    private String name;

    @JsonProperty(RGB_FIELD)
    private Integer rgb;

    @JsonCreator
    public Color(@JsonProperty(value = NAME_FIELD) String name, @JsonProperty(value = RGB_FIELD) Integer rgb) {
        this.name = name;
        this.rgb = rgb;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRgb() {
        return rgb;
    }

    public void setRgb(int rgb) {
        this.rgb = rgb;
    }

    @Override
    public int compareTo(Color o) {
        return getRgb() - o.getRgb();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Color)) return false;
        Color color = (Color) o;
        return rgb == color.rgb &&
                name.equals(color.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rgb);
    }


}
