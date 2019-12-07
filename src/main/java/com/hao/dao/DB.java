package com.hao.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * DB可以创建删除表
 */
public class DB {

    public void createTable(String tableName, Column[] column) {
        try {

            File file = new File(tableName);
            if (file.exists()) {
                throw new RuntimeException("表(" + tableName + ")已存在，不可重复创建");
            }
            FileOutputStream fos = new FileOutputStream(file);
            StringBuilder sb = new StringBuilder();
            for (Column value : column) {
                sb.append(value.getColName()).append(",").append(value.getColType()).append('\t');
            }
            sb.deleteCharAt(sb.length() - 1).append("\n");
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Table getTable(String tableName) {
        return new Table(tableName);
    }

    public void dropTable(String tableName) {
        File file = new File(tableName);
        file.delete();
    }
}
