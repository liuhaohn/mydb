package com.hao.bean.statement;

public interface Statement {

    enum StatementType {
        CREATE,
        DROP,
        SELECT,
        INSERT,
        DELETE,
        UPDATE
    }

    StatementType getType();
}

