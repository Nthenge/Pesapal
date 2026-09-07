package com.challenge.Pesapal.controller;

import com.challenge.Pesapal.service.DatabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("UP");
    }
}

