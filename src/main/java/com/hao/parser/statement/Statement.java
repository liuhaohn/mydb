package com.hao.parser.statement;

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

