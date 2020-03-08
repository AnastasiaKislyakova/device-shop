package ru.kislyakova.anastasia.shop.storage.filter.sorting;

import java.util.Comparator;

public interface SortableFieldProvider<T> {
    Comparator<T> getSortConditional(SortConditional sortConditional);
}
