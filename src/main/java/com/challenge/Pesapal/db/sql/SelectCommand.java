package com.challenge.Pesapal.db.sql;

public class SelectCommand extends SqlCommand {

    private final String tableName;
    private final String whereColumn;
    private final Object whereValue;

    public SelectCommand(String tableName, String whereColumn, Object whereValue) {
        this.tableName = tableName;
        this.whereColumn = whereColumn;
        this.whereValue = whereValue;
    }

    public String getTableName() {
        return tableName;
    }

    public String getWhereColumn() {
        return whereColumn;
    }

    public Object getWhereValue() {
        return whereValue;
    }

}
