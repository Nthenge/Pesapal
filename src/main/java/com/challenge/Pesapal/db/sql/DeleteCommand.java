package com.challenge.Pesapal.db.sql;

public class DeleteCommand extends SqlCommand {

    private final String tableName;
    private final String whereColumn;
    private final Object whereValue;

    public DeleteCommand(String tableName,
                         String whereColumn,
                         Object whereValue) {
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

