package com.challenge.Pesapal.db.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Database {

    private final Map<String, Table> tables = new ConcurrentHashMap<>();

    public void createTable(Table table) {
        Table existing = tables.putIfAbsent(table.getName(), table);
        if (existing != null) {
            throw new RuntimeException("Table already exists: " + table.getName());
        }
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