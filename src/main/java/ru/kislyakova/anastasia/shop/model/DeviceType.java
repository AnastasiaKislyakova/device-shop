package ru.kislyakova.anastasia.shop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum DeviceType {
    SMARTPHONE,
    LAPTOP,
    SMARTWATCHES,
    TABLET;

    private static Map<String, DeviceType> namesMap = new HashMap<String, DeviceType>(4);

    static{
        namesMap.put("smartphone", SMARTPHONE);
        namesMap.put("laptop", LAPTOP);
        namesMap.put("smart watches", SMARTWATCHES);
        namesMap.put("tablet", TABLET);
    }

    @JsonCreator
    public static DeviceType forValue(String value){
        if (value == null) {
            return null;
        }
        return namesMap.get(value.toLowerCase());
    }

    @JsonValue
    public String toValue(){
        for (Map.Entry<String, DeviceType> entry : namesMap.entrySet()) {
            if (entry.getValue() == this) {
                return entry.getKey();
            }
        }
        return null;
    }
}
