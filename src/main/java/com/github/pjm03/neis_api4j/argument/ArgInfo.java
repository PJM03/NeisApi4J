package com.github.pjm03.neis_api4j.argument;

public interface ArgInfo<T> {
    String getName();
    Class<T> getType();
    String valueToString(T value);
}
