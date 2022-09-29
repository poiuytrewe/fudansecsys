package com.fudanuniversity.cms.commons.model.wrapper;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Xinyue.Tang at 2020-06-24 15:00:29
 */
public class TripleTuple<L, M, R> implements Serializable {

    public L left;

    public M middle;

    public R right;

    public TripleTuple() {
    }

    public TripleTuple(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public M getMiddle() {
        return middle;
    }

    public void setMiddle(M middle) {
        this.middle = middle;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    public static <L, M, R> TripleTuple<L, M, R> of(final L left, final M middle, final R right) {
        return new TripleTuple<>(left, middle, right);
    }

    /**
     * <p>Compares this triple to another based on the three elements.</p>
     *
     * @param obj the object to compare to, null returns false
     * @return true if the elements of the triple are equal
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof TripleTuple<?, ?, ?>) {
            final TripleTuple<?, ?, ?> other = (TripleTuple<?, ?, ?>) obj;
            return Objects.equals(getLeft(), other.getLeft())
                    && Objects.equals(getMiddle(), other.getMiddle())
                    && Objects.equals(getRight(), other.getRight());
        }
        return false;
    }

    /**
     * <p>Returns a suitable hash code.</p>
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return (getLeft() == null ? 0 : getLeft().hashCode()) ^
                (getMiddle() == null ? 0 : getMiddle().hashCode()) ^
                (getRight() == null ? 0 : getRight().hashCode());
    }

    /**
     * <p>Returns a String representation of this triple using the format {@code ($left,$middle,$right)}.</p>
     *
     * @return a string describing this object, not null
     */
    @Override
    public String toString() {
        return "(" + getLeft() + "," + getMiddle() + "," + getRight() + ")";
    }

    /**
     * <p>Formats the receiver using the given format.</p>
     *
     * <p>This uses {@link java.util.Formattable} to perform the formatting. Three variables may
     * be used to embed the left and right elements. Use {@code %1$s} for the left
     * element, {@code %2$s} for the middle and {@code %3$s} for the right element.
     * The default format used by {@code toString()} is {@code (%1$s,%2$s,%3$s)}.</p>
     *
     * @param format the format string, optionally containing {@code %1$s}, {@code %2$s} and {@code %3$s}, not null
     * @return the formatted string, not null
     */
    public String toString(final String format) {
        return String.format(format, getLeft(), getMiddle(), getRight());
    }

}
