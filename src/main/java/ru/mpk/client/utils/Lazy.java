package ru.mpk.client.utils;

import java.util.Optional;
import java.util.function.Supplier;

public class Lazy<T> {

    private Supplier<T> supplier;

    private T obj;

    private boolean resolved;

    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        if (!resolved) {
            obj = supplier.get();
            resolved = true;
        }
        return obj;
    }

    public boolean isResolved() {
        return resolved;
    }

    public Optional<T> getOptional() {
        return Optional.ofNullable(get());
    }
}