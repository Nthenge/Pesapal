package com.challenge.Pesapal.db.sql;

import com.challenge.Pesapal.db.core.Column;

import java.util.List;

public class CreateTableCommand extends SqlCommand {
    private final String tableName;
    private final List<Column> columns;

    public CreateTableCommand(String tableName, List<Column> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public List<Column> getColumns() {
        return columns;
    }
}
