package com.hao.parser.statement;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UpdateStatement implements Statement {
    @Override
    public StatementType getType() {
        return Statement.StatementType.UPDATE;
    }

    private String tableName;
    private LinkedHashMap set;
    private List<String> condition;

    public UpdateStatement(String tableName, LinkedHashMap set, List<String> condition) {
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

    public List<String> getCondition() {
        return condition;
    }

    public void setCondition(List<String> condition) {
        this.condition = condition;
    }
}
