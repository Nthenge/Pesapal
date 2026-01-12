package com.challenge.Pesapal;

import com.challenge.Pesapal.db.core.Database;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHolder {
    private final Database database = new Database();
    public Database getDatabase() { return database; }
}

