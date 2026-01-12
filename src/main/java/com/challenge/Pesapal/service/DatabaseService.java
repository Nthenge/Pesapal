package com.challenge.Pesapal.service;

import com.challenge.Pesapal.DatabaseHolder;
import com.challenge.Pesapal.db.engine.QueryExecutor;
import com.challenge.Pesapal.db.sql.SqlParser;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    private final QueryExecutor executor;
    private final SqlParser parser;

    public DatabaseService(DatabaseHolder holder) {
        this.executor = new QueryExecutor(holder.getDatabase());
        this.parser = new SqlParser();
    }

    public Object executeSql(String sql) {
        return executor.execute(parser.parse(sql));
    }
}

