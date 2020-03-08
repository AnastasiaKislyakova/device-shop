package ru.kislyakova.anastasia.shop.service.preprocessing;

public interface Preprocessor<T> {
    public T process(T value);
}
