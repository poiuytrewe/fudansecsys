package com.fudanuniversity.cms.commons.model.wrapper;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * 普通的键值对参数封装
 * <p>
 * Created by Xinyue.Tang at 2020-06-24 14:59:35
 */
public class PairTuple<L, R> implements  Serializable {

    public L left;

    public R right;

    public PairTuple() {
    }

    public PairTuple(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    public static <L, R> PairTuple<L, R> create() {
        return new PairTuple<>();
    }

    public static <L, R> PairTuple<L, R> create(L left, R right) {
        return new PairTuple<>(left, right);
    }

    /**
     * <p>Creates an immutable pair of two objects inferring the generic types.</p>
     *
     * <p>This factory allows the pair to be created using inference to
     * obtain the generic types.</p>
     */
    public static <L, R> PairTuple<L, R> of(final L left, final R right) {
        return new PairTuple<>(left, right);
    }

    /**
     * <p>Creates an immutable pair from an existing pair.</p>
     *
     * <p>This factory allows the pair to be created using inference to
     * obtain the generic types.</p>
     */
    public static <L, R> PairTuple<L, R> of(final Map.Entry<L, R> pair) {
        return new PairTuple<>(pair.getKey(), pair.getValue());
    }

    /**
     * <p>Compares this pair to another based on the two elements.</p>
     *
     * @param obj the object to compare to, null returns false
     * @return true if the elements of the pair are equal
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof PairTuple<?, ?>) {
            final PairTuple<?, ?> other = (PairTuple<?, ?>) obj;
            return Objects.equals(getLeft(), other.getLeft())
                    && Objects.equals(getRight(), other.getRight());
        }
        return false;
    }

    /**
     * <p>Returns a String representation of this pair using the format {@code ($left,$right)}.</p>
     *
     * @return a string describing this object, not null
     */
    @Override
    public String toString() {
        return "(" + getLeft() + ',' + getRight() + ')';
    }

    /**
     * <p>Formats the receiver using the given format.</p>
     *
     * <p>This uses {@link java.util.Formattable} to perform the formatting. Two variables may
     * be used to embed the left and right elements. Use {@code %1$s} for the left
     * element (key) and {@code %2$s} for the right element (value).
     * The default format used by {@code toString()} is {@code (%1$s,%2$s)}.</p>
     *
     * @param format the format string, optionally containing {@code %1$s} and {@code %2$s}, not null
     * @return the formatted string, not null
     */
    public String toString(final String format) {
        return String.format(format, getLeft(), getRight());
    }
}
