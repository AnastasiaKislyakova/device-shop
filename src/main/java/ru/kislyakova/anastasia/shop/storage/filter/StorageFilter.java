package ru.kislyakova.anastasia.shop.storage.filter;

import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortConditional;
import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortableFieldProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class StorageFilter<T> {
    private List<Conditional<T>> conditions = new ArrayList<>();
    private List<Comparator<T>> sortings = new ArrayList<>();
    private int count = Integer.MAX_VALUE;
    private int pageNumber = 0;

    public List<Conditional<T>> getConditions() {
        return conditions;
    }

    public List<Comparator<T>> getSortings() {
        return sortings;
    }

    public void addCondition(Conditional<T> conditional) {
        conditions.add(conditional);
    }

    public void addSorting(SortableFieldProvider<T> provider, SortConditional conditional) {
        Comparator<T> sorting = provider.getSortConditional(conditional);
        if (conditional.getOrder() == SortConditional.Order.DESC) {
            sorting = sorting.reversed();
        }
        sortings.add(sorting);
    }

    public void addAllSorting(SortableFieldProvider<T> provider, List<SortConditional> conditionals) {
        conditionals.forEach((o) -> addSorting(provider, o));
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getCount() {
        return count;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StorageFilter)) return false;
        StorageFilter<?> that = (StorageFilter<?>) o;
        return count == that.count &&
                pageNumber == that.pageNumber &&
                Objects.equals(conditions, that.conditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conditions, sortings, count, pageNumber);
    }

    @Override
    public String toString() {
        return "StorageFilter{" +
                "conditions=" + conditions +
                ", count=" + count +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
