package com.hao.util;

import com.hao.dao.Column;

import java.util.ArrayList;
import java.util.List;

public class ColumnsBuilder {
    private List<Column> columns = new ArrayList<>();

    public ColumnsBuilder appendColumn(String cName){
        Column c = new Column(cName, "string");
        columns.add(c);
        return this;
    }

    public ColumnsBuilder appendColumn(String cName,String type){
        Column c = new Column(cName, type);
        columns.add(c);
        return this;
    }

    public Column[] toColumnArray(){
        return columns.toArray(new Column[0]);
    }
}
