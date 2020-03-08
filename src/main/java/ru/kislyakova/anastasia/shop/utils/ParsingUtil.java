package ru.kislyakova.anastasia.shop.utils;

import ru.kislyakova.anastasia.shop.model.date.DateTimeDeserializer;
import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortConditional;
import ru.kislyakova.anastasia.shop.model.date.DateDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class with some parsing methods
 */

public class ParsingUtil {

    /**
     * Parse LocalDate from String
     * @param dateStr String to be parsed
     * @return LocalDate date
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static LocalDate getLocalDate(String dateStr){
        LocalDate date = null;
        if (dateStr != null) {
            date = LocalDate.parse(dateStr, DateDeserializer.formatter);
        }
        return date;
    }

    /**
     * Parses LocalDateTime from String
     * @param dateTimeStr string to be parsed
     * @return LocalDateTime date
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static LocalDateTime getLocalDateTime(String dateTimeStr){
        LocalDateTime dateTime = null;
        if (dateTimeStr != null) {
            dateTime = LocalDateTime.parse(dateTimeStr, DateTimeDeserializer.formatter);
        }
        return dateTime;
    }

    /**
     * Parses sorting params split by "," from String
     * @param sortBy string to be parsed
     * @return List of sort conditionals
     */
    public static List<SortConditional> getSortParams(String sortBy){
        if (sortBy == null) {
            return Collections.emptyList();
        }
        List<SortConditional> sortConditionals = new ArrayList<>();
        String[] params = sortBy.split(",");
        for (String param : params){
            SortConditional.Order order;
            String field;
            if (param.charAt(0) == '-') {
                field = param.substring(1);
                order = SortConditional.Order.DESC;
            } else {
                field = param;
                order = SortConditional.Order.ASC;
            }
            sortConditionals.add(new SortConditional(field, order));
        }

        return sortConditionals;
    }

    /**
     * Parses devices' ids split by "," from string
     * @param deviceIdsStr string to be parsed
     * @return List of ids
     */
    public static List<Long> getDeviceIds(String deviceIdsStr){
        if (deviceIdsStr == null){
            return Collections.emptyList();
        }
        List<Long> deviceIds = new ArrayList<>();
        String[] params = deviceIdsStr.split(",");
        for (String param : params){
            deviceIds.add(Long.parseLong(param));
        }
        return deviceIds;
    }


}
