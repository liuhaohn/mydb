package com.hao.dao;

import com.hao.bean.Data;
import com.hao.bean.statement.*;
import com.hao.dao.Column;
import com.hao.dao.DB;
import com.hao.dao.Table;
import com.hao.parser.HaoQLParser;
import com.hao.parser.ParseException;
import com.hao.util.BeanUtils;
import com.hao.util.SelectUtils;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Process {
    private DB db;

    private BufferedReader reader;
    private PrintWriter writer;

    public Process(DB db, BufferedReader reader, PrintWriter out) {
        this.db = db;
        this.reader = reader;
        this.writer = out;
    }


    public void run() {

        writer.println("########################################");
        writer.println("############# That is HaoQL ############");
        writer.println("########################################");
        writer.println();
        writer.flush();
        HaoQLParser parser = new HaoQLParser(new StringReader(""));
        Statement statement;
        String input = null;


        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                writer.print("HaoQL>> ");
                writer.flush();
                while (true) {
                    String line = reader.readLine();
                    if (line.contains(";")) {
                        sb.append(line.split(";", 2)[0]);
                        break;
                    } else {
                        sb.append(line).append(" ");
                        writer.print("     >> ");
                        writer.flush();
                    }
                }


                if ("exit".equals(sb.toString().toLowerCase())) {
                    writer.println("Thanks for using HaoSQL");
                    writer.flush();
                    return;
                } else {
                    parser.ReInit(new StringReader(sb.toString()));
                    sb = new StringBuilder();
                    statement = parser.init();
                    processStatements(statement);
                }
                writer.println();
                writer.flush();
            } catch (ParseException e) {
                writer.println("Error in parsing the query\r\n");
                e.printStackTrace();
            } catch (Throwable ex) {
                writer.println("Error\r\n");
                ex.printStackTrace();
            }
        }
    }

    private void processStatements(Statement stmt) {

        switch (stmt.getType()) {
            case CREATE: {
                CreateStatement stm = (CreateStatement) stmt;
                try {
                    db.createTable(stm.getTableName(), BeanUtils.mapToColumns(stm.getAttributes()));
                    writer.println("table " + stm.getTableName()+" created successfully");
                    writer.flush();
                } catch (Exception e) {
                    System.out.println("create table " + stm.getTableName() + " failed, message:" + e.getMessage());
                }
                break;
            }
            case DROP: {
                DropStatement stm = (DropStatement) stmt;
                db.dropTable(stm.getTableName());
                writer.println("table " + stm.getTableName()+" deleted successfully");
                writer.flush();
                break;
            }
            case INSERT: {
                InsertStatement stm = (InsertStatement) stmt;
                Table table = db.getTable(stm.getTableName());
                Data data = new Data();
                if (stm.getSelectStatement() == null) {
                    if (stm.getAttributes() == null) {
                        data.columns = table.columns;
                    } else {
                        data.columns = BeanUtils.listToColumns(stm.getAttributes());
                    }
                    data.data = BeanUtils.llistsToArraylists(stm.getValues());
                } else {
                    data = selectProcess(stm.getSelectStatement());
                }
                table.insert(data);
                writer.println("insert " + data.data.size() + " records successfully");
                writer.flush();
                break;
            }
            case SELECT: {
                SelectStatement stm = (SelectStatement) stmt;
                Data data = selectProcess(stm);
                writer.println(data);
                writer.println("find " + data.data.size() + " records successfully");
                writer.flush();
                break;
            }
            case DELETE: {
                DeleteStatement stm = (DeleteStatement) stmt;
                Table table = db.getTable(stm.getTableName());
                int count = table.delete(((DeleteStatement) stmt).getCondition());
                writer.println("remove " + count + " records successfully");
                writer.flush();
                break;
            }
            case UPDATE: {
                UpdateStatement stm = (UpdateStatement) stmt;
                Column[] columns = BeanUtils.listToColumns(new ArrayList(stm.getSet().keySet()));
                Data data = new Data();
                data.data = new ArrayList<>();
                data.data.add(stm.getSet().values().toArray());
                data.columns = columns;
                int count = db.getTable(stm.getTableName()).update(data, stm.getCondition());
                writer.println("update " + count + " records successfully");
                writer.flush();
                break;
            }

        }
    }

    private Data selectProcess(SelectStatement stm) {
        Table table = db.getTable(stm.getTables().get(0));
        Column[] columns;
        if (stm.getColumns().get(0).equals("*")) {
            columns = table.columns;
        } else {
            columns = BeanUtils.listToColumns(stm.getColumns());
        }
        Data data = SelectUtils.selectColumn(table.selectAll(), columns);
        List<List<Expression>> condition = stm.getCondition();
        if (condition != null) {
            data = SelectUtils.selectRow(data, condition);
        }
        return data;
    }

}