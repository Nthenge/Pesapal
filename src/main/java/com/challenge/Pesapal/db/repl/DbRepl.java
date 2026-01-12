package com.challenge.Pesapal.db.repl;

import com.challenge.Pesapal.db.core.Database;
import com.challenge.Pesapal.db.core.Row;
import com.challenge.Pesapal.db.engine.QueryExecutor;
import com.challenge.Pesapal.db.sql.SqlCommand;
import com.challenge.Pesapal.db.sql.SqlParser;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DbRepl {

    public static void main(String[] args) {

        Database database = new Database();
        QueryExecutor executor = new QueryExecutor(database);
        SqlParser parser = new SqlParser();

        Scanner scanner = new Scanner(System.in);

        System.out.println("MiniDB started. Type 'exit' to quit.");

        while (true) {
            System.out.print("db> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Bye 👋");
                break;
            }

            if (input.isEmpty()) {
                continue;
            }

            try {
                SqlCommand command = parser.parse(input);
                Object result = executor.execute(command);
                printResult(result);
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void printResult(Object result) {

        if (result == null) {
            return;
        }

        if (result instanceof String) {
            System.out.println(result);
            return;
        }

        if (result instanceof List) {
            List<?> list = (List<?>) result;

            if (list.isEmpty()) {
                System.out.println("(no rows)");
                return;
            }

            for (Object row : list) {
                if (row instanceof Row) {
                    System.out.println(
                            ((Row) row).getValues()
                    );
                } else {
                    System.out.println(row);
                }
            }
            return;
        }

        if (result instanceof Map) {
            System.out.println(result);
            return;
        }

        System.out.println(result.toString());
    }
}

