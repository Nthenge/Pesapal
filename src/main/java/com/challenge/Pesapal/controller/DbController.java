package com.challenge.Pesapal.controller;

import com.challenge.Pesapal.service.DatabaseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/db")
public class DbController {

    private final DatabaseService service;

    public DbController(DatabaseService service) {
        this.service = service;
    }

    @PostMapping("/execute")
    public Object execute(@RequestBody String sql) {
        return service.executeSql(sql);
    }
}

