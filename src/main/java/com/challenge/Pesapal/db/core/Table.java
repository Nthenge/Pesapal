package com.challenge.Pesapal.db.core;

import java.util.*;

public class Table {

    private final String name;
    private final List<Column> columns;
    private final List<Row> rows = new ArrayList<>();

    private final Map<String, Map<Object, Row>> indexes = new HashMap<>();
    private final Map<String, Integer> lastIds = new HashMap<>();


    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;

        for (Column column : columns){
            if (column.isPrimaryKey() || column.isUnique()){
                indexes.put(column.getName(), new HashMap<>());
            }
            if (column.isPrimaryKey()) {
                lastIds.put(column.getName(), 0); // start auto-increment from 1
            }
        }
    }

    public void insert(Row row) {
        for (Map.Entry<String, Map<Object, Row>> entry : indexes.entrySet()) {
            String columnName = entry.getKey();
            Object value = row.get(columnName);

            if (entry.getValue().containsKey(value)) {
                throw new RuntimeException(
                        "Duplicate value for unique column: " + columnName
                );
            }
        }

        rows.add(row);

        for (Map.Entry<String, Map<Object, Row>> entry : indexes.entrySet()) {
            entry.getValue().put(row.get(entry.getKey()), row);
        }
    }

    public int getNextId(String primaryKeyColumn) {
        if (!lastIds.containsKey(primaryKeyColumn)) {
            throw new RuntimeException("Column is not a primary key: " + primaryKeyColumn);
        }
        int next = lastIds.get(primaryKeyColumn) + 1;
        lastIds.put(primaryKeyColumn, next);
        return next;
    }

    public void updateIndex(String columnName, Object oldValue, Object newValue, Row row) {
        Map<Object, Row> index = indexes.get(columnName);
        if (index == null) return;

        if (newValue != null && index.containsKey(newValue) && index.get(newValue) != row) {
            throw new RuntimeException("Duplicate value for unique column: " + columnName);
        }

        index.remove(oldValue);
        index.put(newValue, row);
    }

    public void removeFromIndexes(Row row) {
        for (Map.Entry<String, Map<Object, Row>> entry : indexes.entrySet()) {
            entry.getValue().remove(row.get(entry.getKey()));
        }
    }

    public List<Row> getRows() {
        return rows;
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public Optional<Row> findByIndexedColumn(String columnName, Object value) {
        if (!indexes.containsKey(columnName)) {
            return Optional.empty();
        }
        return Optional.ofNullable(indexes.get(columnName).get(value));
    }
}
