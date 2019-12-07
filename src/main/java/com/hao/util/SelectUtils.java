package com.hao.util;

import com.hao.bean.Data;
import com.hao.dao.Column;
import com.hao.parser.statement.SelectStatement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectUtils {
    public static Data selectColumn(Data data,Column[] columns){
        Data res = new Data();
        res.columns=columns;
        res.data=new ArrayList<>();
        List<Column> dcl = Arrays.asList(data.columns);
        for (int j = 0; j < data.data.size(); j++) {
            Object[] objects = new Object[columns.length];
            for (int i = 0; i < columns.length; i++) {
                objects[i] = data.data.get(j)[dcl.indexOf(columns[i])];
            }
            res.data.add(j, objects);
        }
        return res;
    }

    public static Data selectRow(Data data, Data row){
        Data res = new Data();
        res.columns=data.columns;
        res.data=new ArrayList<>();
        for (int i = 0; i < data.data.size(); i++) {
            Object[] objects = data.data.get(i);
            if (matchCondition(data.columns,objects,row)){
                res.data.add(objects);
            }
        }
        return res;
    }

    public static Data selectByStatement(Data data, SelectStatement stmt){

        return null;
    }

    public static boolean matchCondition(Column[] columns, Object[] objects, Data condition) {
        List<Column> cdtc = Arrays.asList(condition.columns);
        for (int i = 0, i1 = -1; i < columns.length; i++) {
            if ((i1 = cdtc.indexOf(columns[i])) != -1) {
                if (!condition.data.get(0)[i1].equals(objects[i])) return false;
            }
        }
        return true;
    }

}
