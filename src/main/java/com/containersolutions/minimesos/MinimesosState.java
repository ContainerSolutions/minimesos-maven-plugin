package com.containersolutions.minimesos;

import java.util.Map;
import java.util.Optional;

public class  MinimesosState<T> {
    private final String key;

    public MinimesosState(String key) {
        this.key = key;
    }

    @SuppressWarnings("unchecked")
    public void put(Map pluginContext, T value) {
        pluginContext.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public void delete(Map pluginContext) {
        pluginContext.remove(key);
    }

    @SuppressWarnings("unchecked")
    public Optional<T> get(Map pluginContext) {
        T value = (T) pluginContext.get(key);
        return Optional.ofNullable(value);
    }
}
