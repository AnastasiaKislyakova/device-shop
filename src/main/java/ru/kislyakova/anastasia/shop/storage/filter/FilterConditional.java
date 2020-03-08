package ru.kislyakova.anastasia.shop.storage.filter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class FilterConditional<T, R> implements Conditional<T> {
    private final Function<T, R> extractor;
    private final List<Conditional<R>> conditional;

    private FilterConditional(Function<T, R> extractor, List<Conditional<R>> conditional) {
        this.extractor = extractor;
        this.conditional = conditional;
    }

    private FilterConditional(Function<T, R> extractor, Conditional<R> conditional) {
        this(extractor, Collections.singletonList(conditional));
    }

    public Function<T, R> getExtractor() {
        return extractor;
    }

    public List<Conditional<R>> getConditional() {
        return conditional;
    }

    @Override
    public boolean accept(T object) {
        R value = extractor.apply(object);
        return conditional.stream().allMatch(it -> it.accept(value));
    }

    public static <T, R extends Comparable<? super R>> FilterConditionalBuilder<T, R> on(Function<T, R> extractor) {
        return new FilterConditionalBuilder<>(extractor);
    }

    public static class FilterConditionalBuilder<T, R extends Comparable<? super R>> {
        private final Function<T, R> extractor;

        private FilterConditionalBuilder(Function<T, R> extractor) {
            this.extractor = extractor;
        }

        public FilterConditional<T, R> cond(Conditional<R> conditional) {
            return new FilterConditional<>(extractor, conditional);
        }

        public FilterConditional<T, R> conds(List<Conditional<R>> conditionals) {
            return new FilterConditional<>(extractor, conditionals);
        }

        public FilterConditional<T, R> eq(R value) {
            return cond(ComparisonConditional.eq(value));
        }

        public FilterConditional<T, R> inRange(R from, R to) {
            return cond(new RangeConditional<>(from, to));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilterConditional)) return false;
        FilterConditional<?, ?> that = (FilterConditional<?, ?>) o;
        return Objects.equals(conditional, that.conditional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extractor, conditional);
    }

    @Override
    public String toString() {
        return "FilterConditional{" + conditional + '}';
    }
}

