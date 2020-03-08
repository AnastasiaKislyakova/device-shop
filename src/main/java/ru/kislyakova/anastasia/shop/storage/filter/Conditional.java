package ru.kislyakova.anastasia.shop.storage.filter;

public interface Conditional<T> {
    boolean accept(T object);
}
