package com.challenge.Pesapal.db.engine;

import com.challenge.Pesapal.db.core.*;
import com.challenge.Pesapal.db.sql.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryExecutor {

        private final Database database;

        public QueryExecutor(Database database) {
            this.database = database;
        }

        public Object execute(SqlCommand command) {

            if (command instanceof CreateTableCommand) {
                return executeCreateTable((CreateTableCommand) command);
            }

            if (command instanceof InsertCommand) {
                return executeInsert((InsertCommand) command);
            }

            if (command instanceof SelectCommand) {
                return executeSelect((SelectCommand) command);
            }

            if (command instanceof UpdateCommand) {
                return executeUpdate((UpdateCommand) command);
            }

            if (command instanceof DeleteCommand) {
                return executeDelete((DeleteCommand) command);
            }


            throw new RuntimeException("Unsupported command: " + command.getClass());
        }

    private Object executeDelete(DeleteCommand command) {

        Table table = database.getTable(command.getTableName());

        int before = table.getRows().size();

        table.getRows().removeIf(row ->
                row.get(command.getWhereColumn())
                        .equals(command.getWhereValue())
        );

        int after = table.getRows().size();

        return (before - after) + " row(s) deleted";
    }

    private Object executeUpdate(UpdateCommand command) {

        Table table = database.getTable(command.getTableName());
        int count = 0;

        for (Row row : table.getRows()) {
            if (row.get(command.getWhereColumn())
                    .equals(command.getWhereValue())) {

                for (var entry : command.getNewValues().entrySet()) {
                    row.set(entry.getKey(), entry.getValue());
                }
                count++;
            }
        }

        return count + " row(s) updated";
    }


    private Object executeCreateTable(CreateTableCommand command) {

            Table table = new Table(
                    command.getTableName(),
                    command.getColumns()
            );

            database.createTable(table);
            return "Table created: " + command.getTableName();
        }


        private Object executeInsert(InsertCommand command) {

            Table table = database.getTable(command.getTableName());
            List<Column> columns = table.getColumns();
            List<Object> values = command.getValues();

            if (columns.size() != values.size()) {
                throw new RuntimeException("Column count does not match values count");
            }

            Map<String, Object> rowValues = new HashMap<>();

            for (int i = 0; i < columns.size(); i++) {
                Column column = columns.get(i);
                Object value = values.get(i);

                validateType(column, value);
                rowValues.put(column.getName(), value);
            }

            table.insert(new Row(rowValues));
            return "Row inserted";
        }


        private Object executeSelect(SelectCommand command) {

            Table table = database.getTable(command.getTableName());

            if (command.getWhereColumn() == null) {
                return table.getRows();
            }

            return table.getRows().stream()
                    .filter(row ->
                            command.getWhereValue().equals(
                                    row.get(command.getWhereColumn())
                            )
                    )
                    .collect(Collectors.toList());
        }

        private void validateType(Column column, Object value) {

            if (column.getDataTypes() == DbDataTypes.INT && !(value instanceof Integer)) {
                throw new RuntimeException(
                        "Invalid type for column " + column.getName() +
                                ". Expected INT"
                );
            }

            if (column.getDataTypes() == DbDataTypes.TEXT && !(value instanceof String)) {
                throw new RuntimeException(
                        "Invalid type for column " + column.getName() +
                                ". Expected TEXT"
                );
            }
        }
}

