package com.hao.bean;

import com.hao.dao.Column;

import java.util.List;

public class Data {
    public Column[] columns;
    public List<Object[]> data;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Column col : columns) {
            sb.append(col.getColName()).append("\t");
        }
        sb.deleteCharAt(sb.length() - 1).append("\r\n");
        for (Object[] objs : data) {
            for (Object obj : objs) {
                sb.append(obj).append("\t");
            }
            sb.deleteCharAt(sb.length() - 1).append("\r\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
