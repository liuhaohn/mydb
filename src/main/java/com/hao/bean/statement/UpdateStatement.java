package com.hao.bean.statement;

import java.util.LinkedHashMap;
import java.util.List;

public class UpdateStatement implements Statement {
    @Override
    public StatementType getType() {
        return Statement.StatementType.UPDATE;
    }

    private String tableName;
    private LinkedHashMap set;
    private List<List<Expression>> condition;

    public UpdateStatement(String tableName, LinkedHashMap set, List<List<Expression>> condition) {
        this.tableName = tableName;
        this.set = set;
        this.condition = condition;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public LinkedHashMap getSet() {
        return set;
    }

    public void setSet(LinkedHashMap set) {
        this.set = set;
    }

    public List<List<Expression>> getCondition() {
        return condition;
    }

    public void setCondition(List<List<Expression>> condition) {
        this.condition = condition;
    }
}
