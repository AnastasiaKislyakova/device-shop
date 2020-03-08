package ru.kislyakova.anastasia.shop.storage.filter.sorting;

import java.util.Objects;

public class SortConditional {
    private String field;
    private Order order;

    public SortConditional(String field, Order order) {
        this.field = field;
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public Order getOrder() {
        return order;
    }

    public enum Order{
        ASC,
        DESC
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SortConditional)) return false;
        SortConditional that = (SortConditional) o;
        return Objects.equals(field, that.field) &&
                order == that.order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, order);
    }

    @Override
    public String toString() {
        return "SortConditional{" +
                "field='" + field + '\'' +
                ", order=" + order +
                '}';
    }
}
