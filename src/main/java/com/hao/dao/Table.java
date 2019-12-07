package com.hao.dao;

import com.hao.bean.Data;
import com.hao.util.IOUtils;
import com.hao.util.SelectUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Table使用前需要通过open打开，使用后需要close。
 * Table打开后可以多次查询，更改
 */
public class Table {
    public Column[] columns;
    public String tableName;
    private FileInputStream fis;

    public Table(String tableName) {
        this.tableName = tableName;
        File file = new File(tableName);
        if (!file.exists()) {
            throw new RuntimeException("表(" + tableName + ")不存在，无法打开");
        }
        open();
        Scanner scanner = new Scanner(fis);
        String s = scanner.nextLine();
        scanner.close();
        close();
        String[] cols = s.split("\t");
        columns = new Column[cols.length];
        for (int i = 0; i < cols.length; i++) {
            String[] nt = cols[i].split(",");
            columns[i] = new Column(nt[0], nt[1]);
        }
    }


    private void open() {

        try {
            fis = new FileInputStream(tableName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void truncate() {
        open();
        Scanner scanner = new Scanner(fis);
        String s = scanner.nextLine();
        File file = new File(tableName + ".tmp");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write((s + "\n").getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        close();
        new File(tableName).delete();
        file.renameTo(new File(tableName));
    }

    public void insert(Data data) {
        open();
        File file = new File(tableName + ".tmp");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            IOUtils.copyFully(fis, fos);
            StringBuilder sb = new StringBuilder();
            List<Column> dc = Arrays.asList(data.columns);
            for (Object[] obj : data.data) {
                if (obj != null) {
                    for (int i = 0, i1 = -1; i < this.columns.length; i++) {
                        if ((i1 = dc.indexOf(columns[i])) != -1) {
                            sb.append(obj[i1]).append("\t");
                        } else {
                            sb.append("\0").append("\t");
                        }
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append("\n");
                }
            }
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        close();
        new File(tableName).delete();
        file.renameTo(new File(tableName));
    }


    /**
     * 仅支持获取所有数据（将数据读到内存），需要进行复杂查询使用其他
     */
    public Data selectAll() {
        Data res = new Data();
        res.columns = columns;
        open();
        Scanner scanner = new Scanner(fis);
        scanner.nextLine();
        res.data = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] split = line.split("\t");
            res.data.add(split);
        }
        close();
        return res;
    }

    public void update(Data update, Data condition) {
        Data all = selectAll();
        for (int i = 0; i < all.data.size(); i++) {
            Object[] objects = all.data.get(i);

            if (SelectUtils.matchCondition(columns, objects, condition)) { // 符合条件的行更改
                List<Column> udtc = Arrays.asList(update.columns);
                for (int j = 0, i1; j < objects.length; j++) {
                    if ((i1 = udtc.indexOf(columns[j])) != -1) {
                        objects[j] = update.data.get(0)[i1];
                    }
                }

            }

            all.data.set(i, objects);
        }
        truncate();
        insert(all);
    }

    public void delete(Data condition) {
        Data all = selectAll();
        for (int i = 0; i < all.data.size(); i++) {
            Object[] objects = all.data.get(i);
            if (SelectUtils.matchCondition(columns, objects, condition)) { // 符合条件的行更改
                objects = null;
            }
            all.data.set(i, objects);
        }
        truncate();
        insert(all);
    }


}
