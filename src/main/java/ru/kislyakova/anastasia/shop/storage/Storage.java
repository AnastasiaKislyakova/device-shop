package ru.kislyakova.anastasia.shop.storage;

import ru.kislyakova.anastasia.shop.storage.filter.StorageFilter;

import java.util.List;

public interface Storage<T extends Unique> {
    void save(T object);

    List<T> get(StorageFilter<T> filter);

    T getById(long id);
}
