package com.challenge.Pesapal.db.sql;

import java.util.List;

public class InsertCommand extends SqlCommand {

    private final String tableName;
    private final List<Object> values;

    public InsertCommand(String tableName, List<Object> values) {
        this.tableName = tableName;
        this.values = values;
    }

    public String getTableName() {
        return tableName;
    }

    public List<Object> getValues() {
        return values;
    }

}
