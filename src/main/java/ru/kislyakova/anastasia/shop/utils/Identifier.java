package ru.kislyakova.anastasia.shop.utils;

import java.util.concurrent.atomic.AtomicLong;

public class Identifier {
    private AtomicLong id = new AtomicLong(1);

    public long nextId(){
        return id.getAndIncrement();
    }
}
