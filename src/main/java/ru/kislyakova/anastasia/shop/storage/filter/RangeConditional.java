package ru.kislyakova.anastasia.shop.storage.filter;

import java.util.Objects;

public class RangeConditional<T extends Comparable<? super T>> implements Conditional<T> {
    private final T from;
    private final T to;

    public RangeConditional(T from, T to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean accept(T object) {
        boolean accept = true;
        if (from != null) {
            accept = from.compareTo(object) <= 0;
        }
        if (to != null) {
            accept &= to.compareTo(object) >= 0;
        }
        return accept;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RangeConditional)) return false;
        RangeConditional<?> that = (RangeConditional<?>) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "RangeConditional{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
