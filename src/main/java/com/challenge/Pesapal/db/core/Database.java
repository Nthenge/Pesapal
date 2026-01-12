package com.challenge.Pesapal.db.core;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private final Map<String, Table> tables = new HashMap<>();

    public void createTable(Table table) {
        if (tables.containsKey(table.getName())) {
            throw new RuntimeException("Table already exists: " + table.getName());
        }
        tables.put(table.getName(), table);
    }

    public Table getTable(String tableName) {
        Table table = tables.get(tableName);
        if (table == null) {
            throw new RuntimeException("Table not found: " + tableName);
        }
        return table;
    }

    public boolean tableExists(String tableName) {
        return tables.containsKey(tableName);
    }

}
