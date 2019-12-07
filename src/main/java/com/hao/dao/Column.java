package com.hao.dao;

import java.util.LinkedHashMap;

public class Column {
    private String colName;
    private String colType;


    public Column() {
    }


    public Column(String colName, String colType) {
        this.colName = colName;
        this.colType = colType;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColType() {
        return colType;
    }

    public void setColType(String colType) {
        this.colType = colType;
    }

    @Override
    public boolean equals(Object obj) {
        return colName.equals(((Column) obj).colName);
    }
}
