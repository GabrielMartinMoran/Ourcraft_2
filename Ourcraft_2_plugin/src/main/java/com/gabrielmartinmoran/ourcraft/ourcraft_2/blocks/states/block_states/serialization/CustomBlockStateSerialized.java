package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.serialization;

import java.util.HashMap;

public class CustomBlockStateSerialized {
    public String className;
    protected HashMap<String, String> data;

    public CustomBlockStateSerialized(String className) {
        this.className = className;
        this.data = new HashMap<>();
    }

    public void put(String key, String value) {
        this.data.put(key, value);
    }

    public String get(String key) {
        return this.data.get(key);
    }


}
