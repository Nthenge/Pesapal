package com.challenge.Pesapal.db.sql;

import java.util.Map;

public class UpdateCommand extends SqlCommand {

    private final String tableName;
    private final Map<String, Object> newValues;
    private final String whereColumn;
    private final Object whereValue;

    public UpdateCommand(String tableName,
                         Map<String, Object> newValues,
                         String whereColumn,
                         Object whereValue) {
        this.tableName = tableName;
        this.newValues = newValues;
        this.whereColumn = whereColumn;
        this.whereValue = whereValue;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, Object> getNewValues() {
        return newValues;
    }

    public String getWhereColumn() {
        return whereColumn;
    }

    public Object getWhereValue() {
        return whereValue;
    }
}

