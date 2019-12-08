package com.hao.util;

import com.hao.bean.Data;
import com.hao.dao.Column;
import com.hao.bean.statement.Expression;
import com.hao.bean.statement.SelectStatement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectUtils {
    public static Data selectColumn(Data data, Column[] columns) {
        Data res = new Data();
        res.columns = columns;
        res.data = new ArrayList<>();
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

    public static Data selectRow(Data data, List<List<Expression>> condition) {
        Data res = new Data();
        res.columns = data.columns;
        res.data = new ArrayList<>();
        for (int i = 0; i < data.data.size(); i++) {
            Object[] objects = data.data.get(i);
            if (or(data.columns, objects, condition)) {
                res.data.add(objects);
            }
        }
        return res;
    }

    public static Data selectByStatement(Data data, SelectStatement stmt) {

        return null;
    }

    public static boolean or(Column[] columns, Object[] objects, List<List<Expression>> condition) {
        boolean res = false;
        for (int i = 0; i < condition.size(); i++) {
            res = res || and(columns, objects, condition.get(i));
        }
        return res;
    }

    private static boolean and(Column[] columns, Object[] objects, List<Expression> condition) {
        boolean res = true;
        for (int i = 0; i < condition.size(); i++) {
            res = res && matchCondition(columns, objects, condition.get(i));
        }
        return res;
    }

    private static boolean matchCondition(Column[] columns, Object[] objects, Expression condition) {
        int i = Arrays.asList(columns).indexOf(new Column(condition.getColumnName(), ""));
        String s = (String) objects[i];
        if (condition.getExpr().size() == 1) {
            return s.equals(condition.getExpr().get(0));
        } else {
            return s.equals(condition.calExpr() + "");
        }
    }


}
