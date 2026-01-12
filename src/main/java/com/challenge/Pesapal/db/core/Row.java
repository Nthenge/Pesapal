package com.challenge.Pesapal.db.core;

import java.util.Map;

public class Row {

    private final Map<String, Object> values;

    public Row(Map<String, Object> values) {
        this.values = values;
    }

    public Object get(String columnName){
        return values.get(columnName);
    }

    public void set(String columnName, Object value){
        values.put(columnName, value);
    }

    public Map<String, Object> getValues(){
        return values;
    }
}
