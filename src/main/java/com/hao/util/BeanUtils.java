package com.hao.util;

import com.hao.dao.Column;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BeanUtils {
    public static Column[] mapToColumns(LinkedHashMap<String, String> set) {
        ArrayList<Column> columns = new ArrayList<>();
        for (Map.Entry<String, String> entry :
                set.entrySet()) {
            columns.add(new Column(entry.getKey(), entry.getValue()));
        }
        return columns.toArray(new Column[]{});
    }

    public static Column[] listToColumns(List<String> keys) {
        ArrayList<Column> columns = new ArrayList<>();
        for (String key :
                keys) {
            columns.add(new Column(key, ""));
        }
        return columns.toArray(new Column[]{});
    }

    public static List<Object[]> llistsToArraylists(List<List<String>> values) {
        ArrayList<Object[]> res = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            Object[] objects = values.get(i).toArray();
            res.add(objects);
        }
        return res;
    }
}
