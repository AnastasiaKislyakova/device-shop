package ru.kislyakova.anastasia.shop.storage.filter;

import java.util.Objects;

public class ComparisonConditional<T extends Comparable<? super T>> implements Conditional<T> {
    private final T value;
    private final Operator operator;

    public ComparisonConditional(T value, Operator operator) {
        this.value = value;
        this.operator = operator;
    }

    @Override
    public boolean accept(T object) {
        int cmp = object.compareTo(value);
        switch (operator) {
            case NOT_EQ:
                return cmp != 0;
            case EQ:
                return cmp == 0;
            case GREATER:
                return cmp > 0;
            case LESS:
                return cmp < 0;
            case LESS_OR_EQ:
                return cmp <= 0;
            case GREATER_OR_EQ:
                return cmp >= 0;
        }
        return false;
    }

    public enum Operator {
        NOT_EQ, EQ, GREATER, LESS, LESS_OR_EQ, GREATER_OR_EQ
    }

    public static <T extends Comparable<? super T>> ComparisonConditional<T> less(T value) {
        return new ComparisonConditional<>(value, Operator.LESS);
    }

    public static <T extends Comparable<? super T>> ComparisonConditional<T> greater(T value) {
        return new ComparisonConditional<>(value, Operator.GREATER);
    }

    public static <T extends Comparable<? super T>> ComparisonConditional<T> eq(T value) {
        return new ComparisonConditional<>(value, Operator.EQ);
    }

    public static <T extends Comparable<? super T>> ComparisonConditional<T> greaterOrEq(T value) {
        return new ComparisonConditional<>(value, Operator.GREATER_OR_EQ);
    }

    public static <T extends Comparable<? super T>> ComparisonConditional<T> lessOrEq(T value) {
        return new ComparisonConditional<>(value, Operator.LESS_OR_EQ);
    }

    public static <T extends Comparable<? super T>> ComparisonConditional<T> notEq(T value) {
        return new ComparisonConditional<>(value, Operator.NOT_EQ);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComparisonConditional)) return false;
        ComparisonConditional<?> that = (ComparisonConditional<?>) o;
        return Objects.equals(value, that.value) &&
                operator == that.operator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, operator);
    }

    @Override
    public String toString() {
        return "ComparisonConditional{" +
                "value=" + value +
                ", operator=" + operator +
                '}';
    }
}
