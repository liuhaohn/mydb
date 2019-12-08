package com.hao.bean.statement;

import java.util.List;

public class DeleteStatement implements Statement {
    @Override
    public StatementType getType() {
        return StatementType.DELETE;
    }

    String tableName;
    List<List<Expression>> condition;

    public DeleteStatement(String tableName, List<List<Expression>> condition) {
        this.tableName = tableName;
        this.condition = condition;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<List<Expression>> getCondition() {
        return condition;
    }

    public void setCondition(List<List<Expression>> condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Delete Statement :" + "\r\n" +
                "tableName = '" + tableName + '\'' + "\r\n" +
                "condition = " + condition + "\r\n" ;
    }
}
