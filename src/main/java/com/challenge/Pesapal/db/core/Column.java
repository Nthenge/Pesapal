package com.challenge.Pesapal.db.core;

public class Column {

    private final String name;
    private final DbDataTypes dataTypes;
    private final boolean primaryKey;
    private final boolean unique;

    public Column(String name, DbDataTypes dataTypes, boolean primaryKey, boolean unique) {
        this.name = name;
        this.dataTypes = dataTypes;
        this.primaryKey = primaryKey;
        this.unique = unique || primaryKey;
    }

    public String getName() {
        return name;
    }

    public DbDataTypes getDataTypes() {
        return dataTypes;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public boolean isUnique() {
        return unique;
    }
}
