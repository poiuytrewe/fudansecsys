package com.fudanuniversity.cms.commons.model.wrapper;

import java.io.Serializable;

/**
 * Created by Xinyue.Tang at 2020-07-03 14:15:01
 */
public class Holder<T> implements Serializable {

    private T value;

    public Holder() {
    }

    public Holder(T value) {
        this.value = value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public static <T> Holder<T> create(T value) {
        return new Holder<>(value);
    }

    @Override
    public String toString() {
        return "Holder{" +
                "value=" + value +
                '}';
    }
}
