package com.challenge.Pesapal.db.sql;

public class JoinSelectCommand extends SqlCommand {

    private final String leftTable;
    private final String rightTable;
    private final String leftColumn;
    private final String rightColumn;

    public JoinSelectCommand(
            String leftTable,
            String rightTable,
            String leftColumn,
            String rightColumn) {
        this.leftTable = leftTable;
        this.rightTable = rightTable;
        this.leftColumn = leftColumn;
        this.rightColumn = rightColumn;
    }

    public String getLeftTable() { return leftTable; }
    public String getRightTable() { return rightTable; }
    public String getLeftColumn() { return leftColumn; }
    public String getRightColumn() { return rightColumn; }
}

