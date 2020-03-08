package ru.kislyakova.anastasia.shop.storage;

import ru.kislyakova.anastasia.shop.storage.filter.Conditional;
import ru.kislyakova.anastasia.shop.storage.filter.StorageFilter;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RuntimeStorage<T extends Unique> implements Storage<T> {
    private List<T> objects = new CopyOnWriteArrayList<>();

    @Override
    public void save(T object) {
        objects.add(object);
    }

    @Override
    public List<T> get(StorageFilter<T> filter) {
        Stream<T> objectStream = objects.stream();

        List<Conditional<T>> conditions = filter.getConditions();
        for (Conditional<T> conditional : conditions) {
            objectStream = objectStream.filter(conditional::accept);
        }

        List<Comparator<T>> sorting = filter.getSortings();
        for (int i = sorting.size() - 1; i >= 0; i--) {
            objectStream = objectStream.sorted(sorting.get(i));
        }

        int count = filter.getCount();
        int pageNumber = filter.getPageNumber();
        objectStream = objectStream.skip(pageNumber * count).limit(count);
        return objectStream.collect(Collectors.toList());
    }

    @Override
    public T getById(long id) {
        return objects.stream().filter(object -> object.getId() == id).findFirst().orElse(null);
    }
}
