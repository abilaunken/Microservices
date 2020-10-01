package com.kamehouse.psnservice.model;

import java.util.function.Consumer;

public interface Cache<T> {
    void salvarCache(T t, Consumer c);
}
