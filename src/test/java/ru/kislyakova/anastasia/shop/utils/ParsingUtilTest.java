package ru.kislyakova.anastasia.shop.utils;

import org.junit.jupiter.api.Test;
import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortConditional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParsingUtilTest {

    @Test
    void should_convert_local_date() {
        assertTrue(parseLocalDate("12.12.2012"));

        assertFalse(parseLocalDate(""));

        assertFalse(parseLocalDate("1.1.2012"));

        assertFalse(parseLocalDate("1-1-2012"));

        assertFalse(parseLocalDate("01.01.201"));
    }

    private static boolean parseLocalDate(String dateStr) {
        try {
            LocalDate date = ParsingUtil.getLocalDate(dateStr);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    @Test
    void should_convert_local_date_time() {

        assertTrue(parseLocalDateTime("12.12.2012 12:12:12"));

        assertFalse(parseLocalDateTime("1.1.2012"));

        assertFalse(parseLocalDateTime(""));

        assertFalse(parseLocalDateTime("12.12.2012 12:12:123"));

        assertFalse(parseLocalDateTime("12-12-2012 12:12:12"));
    }

    private static boolean parseLocalDateTime(String dateTimeStr) {
        try {
            LocalDateTime dateTime = ParsingUtil.getLocalDateTime(dateTimeStr);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    @Test
    void should_get_sort_params(){
        List<SortConditional> sortConditionals = new ArrayList<>();
        sortConditionals.add(new SortConditional("Aaa", SortConditional.Order.ASC));
        sortConditionals.add(new SortConditional("Bbbb", SortConditional.Order.DESC));
        sortConditionals.add(new SortConditional("Cccc", SortConditional.Order.ASC));
        List<SortConditional> actual = ParsingUtil.getSortParams("Aaa,-Bbbb,Cccc");

        assertEquals(sortConditionals, actual);

        actual = ParsingUtil.getSortParams(null);
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    void should_get_device_ids() {
        List<Long> deviceIds = new ArrayList<>();
        deviceIds.add(Long.valueOf(1));
        deviceIds.add(Long.valueOf(2));

        List<Long> actual = ParsingUtil.getDeviceIds("1,2");

        assertEquals(deviceIds, actual);

        actual = ParsingUtil.getDeviceIds(null);
        assertEquals(Collections.emptyList(), actual);

        assertThrows(NumberFormatException.class, () -> ParsingUtil.getDeviceIds("1,2,A"));

        assertThrows(NumberFormatException.class, () -> ParsingUtil.getDeviceIds("1;2"));
    }

}