package com.challenge.Pesapal.db.sql;

import com.challenge.Pesapal.db.core.Column;
import com.challenge.Pesapal.db.core.DbDataTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlParser {

    public SqlCommand parse(String sql) {
        sql = sql.trim();

        if (sql.toUpperCase().startsWith("CREATE TABLE")) {
            return parseCreateTable(sql);
        }
        if (sql.toUpperCase().startsWith("INSERT INTO")) {
            return parseInsert(sql);
        }
        if (sql.toUpperCase().startsWith("SELECT")) {
            return parseSelect(sql);
        }if (sql.toUpperCase().startsWith("UPDATE")) {
            return parseUpdate(sql);
        }
        if (sql.toUpperCase().startsWith("DELETE")) {
            return parseDelete(sql);
        }

        if (sql.toUpperCase().contains(" JOIN ")) {
            return parseJoin(sql);
        }



        throw new RuntimeException("Unsupported SQL: " + sql);
    }

    private SqlCommand parseCreateTable(String sql) {
        Pattern pattern = Pattern.compile(
                "CREATE TABLE (\\w+) \\((.+)\\)",
                Pattern.CASE_INSENSITIVE
        );

        Matcher matcher = pattern.matcher(sql);
        if (!matcher.matches()) {
            throw new RuntimeException("Invalid CREATE TABLE syntax");
        }

        String tableName = matcher.group(1);
        String columnDefs = matcher.group(2);

        String[] parts = columnDefs.split(",");
        List<Column> columns = new ArrayList<>();

        for (String part : parts) {
            String[] tokens = part.trim().split("\\s+");

            String name = tokens[0];
            DbDataTypes type = DbDataTypes.valueOf(tokens[1].toUpperCase());

            boolean primaryKey = part.toUpperCase().contains("PRIMARY KEY");
            boolean unique = part.toUpperCase().contains("UNIQUE");

            columns.add(new Column(name, type, primaryKey, unique));
        }

        return new CreateTableCommand(tableName, columns);
    }

    private Object parseValue(String raw) {
        raw = raw.trim();
        if (raw.startsWith("'") && raw.endsWith("'")) {
            return raw.substring(1, raw.length() - 1);
        }
        return Integer.parseInt(raw);
    }

    private SqlCommand parseInsert(String sql) {
        Pattern pattern = Pattern.compile(
                "INSERT INTO (\\w+) VALUES \\((.+)\\)",
                Pattern.CASE_INSENSITIVE
        );

        Matcher matcher = pattern.matcher(sql);
        if (!matcher.matches()) {
            throw new RuntimeException("Invalid INSERT syntax");
        }

        String tableName = matcher.group(1);
        String valuesPart = matcher.group(2);

        String[] rawValues = valuesPart.split(",");
        List<Object> values = new ArrayList<>();

        for (String raw : rawValues) {
            raw = raw.trim();
            if (raw.startsWith("'") && raw.endsWith("'")) {
                values.add(raw.substring(1, raw.length() - 1));
            } else {
                values.add(Integer.parseInt(raw));
            }
        }

        return new InsertCommand(tableName, values);
    }

    private SqlCommand parseUpdate(String sql) {

        // UPDATE users SET name='Mike' WHERE id=1
        String upper = sql.toUpperCase();

        String tableName = sql.split("\\s+")[1];

        String setPart = sql.substring(
                upper.indexOf("SET") + 3,
                upper.indexOf("WHERE")
        ).trim();

        String wherePart = sql.substring(
                upper.indexOf("WHERE") + 5
        ).trim();

        String[] setTokens = setPart.split(",");
        Map<String, Object> newValues = new java.util.HashMap<>();

        for (String token : setTokens) {
            String[] kv = token.split("=");
            String column = kv[0].trim();
            Object value = parseValue(kv[1].trim());
            newValues.put(column, value);
        }

        String[] whereTokens = wherePart.split("=");
        String whereColumn = whereTokens[0].trim();
        Object whereValue = parseValue(whereTokens[1].trim());

        return new UpdateCommand(tableName, newValues, whereColumn, whereValue);
    }

    private SqlCommand parseDelete(String sql) {

        // DELETE FROM users WHERE id=1
        String[] tokens = sql.split("\\s+");

        String tableName = tokens[2];

        String wherePart = sql.substring(
                sql.toUpperCase().indexOf("WHERE") + 5
        ).trim();

        String[] whereTokens = wherePart.split("=");

        String whereColumn = whereTokens[0].trim();
        Object whereValue = parseValue(whereTokens[1].trim());

        return new DeleteCommand(tableName, whereColumn, whereValue);
    }

    private SqlCommand parseJoin(String sql) {

        // SELECT * FROM orders JOIN users ON orders.user_id = users.id
        String[] parts = sql.split("\\s+");

        String leftTable = parts[3];
        String rightTable = parts[5];

        String onPart = sql.substring(sql.toUpperCase().indexOf("ON") + 2).trim();
        String[] condition = onPart.split("=");

        String leftColumn = condition[0].trim().split("\\.")[1];
        String rightColumn = condition[1].trim().split("\\.")[1];

        return new JoinSelectCommand(
                leftTable, rightTable, leftColumn, rightColumn
        );
    }


    private SqlCommand parseSelect(String sql) {
        Pattern pattern = Pattern.compile(
                "SELECT \\* FROM (\\w+)( WHERE (\\w+) = (.+))?",
                Pattern.CASE_INSENSITIVE
        );

        Matcher matcher = pattern.matcher(sql);
        if (!matcher.matches()) {
            throw new RuntimeException("Invalid SELECT syntax");
        }

        String tableName = matcher.group(1);
        String whereColumn = matcher.group(3);
        Object whereValue = null;

        if (matcher.group(4) != null) {
            String raw = matcher.group(4).trim();
            if (raw.startsWith("'")) {
                whereValue = raw.substring(1, raw.length() - 1);
            } else {
                whereValue = Integer.parseInt(raw);
            }
        }

        return new SelectCommand(tableName, whereColumn, whereValue);
    }
}
