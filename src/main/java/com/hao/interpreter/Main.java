package com.hao.interpreter;

import com.hao.bean.Data;
import com.hao.dao.Column;
import com.hao.dao.DB;
import com.hao.dao.Table;
import com.hao.parser.HaoQLParser;
import com.hao.parser.ParseException;
import com.hao.parser.statement.*;
import com.hao.util.BeanUtils;
import com.hao.util.SelectUtils;

import java.io.StringReader;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main implements Runnable {

    /*    public static void main(String[] args) {
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("------------------------ Welcome to TinySQL Interpreter ------------------------");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("");

            Procedures procedures = new Procedures(null, null, null);
            HaoQLParser parser = new HaoQLParser(new StringReader("Dummy"));
            ArrayList<Statement> statements;
            String input = null;

            while (true) {
                System.out.println("\n Input Options: \n 1. Single Query \n 2. File upload " +
                        "\n Press 0 to exit \n \n Choose (1/2)?");
                try {
                    switch (new Scanner(System.in).nextInt()) {
                        case 0:
                            System.out.println(" Thanks for using TinySQL interpreter");
                            System.exit(0);
                            break;
                        case 1:
                            System.out.println(" Enter a TinySQL query:");
                            input = new Scanner(System.in).nextLine();
    //                        System.out.println("Output " + input);
                            parser.ReInit(new StringReader(input));
                            statements = parser.init();
                            processStatements(statements, procedures);
                            break;
                        case 2:
                            System.out.println(" Enter full path to the input file:");
                            input = new Scanner(System.in).nextLine();
    //                        System.out.println("Output " + input);
                            parser = new HaoQLParser(new FileReader(input));
    //                        parser = new TinyParser (new FileReader("test/flow_test.txt"));
                            statements = parser.init();
                            processStatements(statements, procedures);
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                } catch (ParseException e) {
                    System.out.println("\nError in parsing the query ");
                    e.printStackTrace();
                } catch (FileNotFoundException fnf) {
                    System.out.println("\nCould not locate the given file \n" + input);
                } catch (NoSuchElementException | IllegalStateException nse) {
                    System.out.println("\nInvalid Input. Either choose option 1 or option 2");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


        }*/
    private DB db;

    public Main(DB db) {
        this.db = db;
    }

    public static void main(String[] args) {
        new Main(new DB()).run();
    }

    @Override
    public void run() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("----------------------------- Welcome to HaoQL ---------------------------------");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println();

        HaoQLParser parser = new HaoQLParser(new StringReader(""));
        Statement statement;
        String input = null;

        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                System.out.print("HaoQL>> ");

                while (true) {
                    String line = scanner.nextLine();
                    if (line.contains(";")) {
                        sb.append(line.split(";", 2)[0]);
                        break;
                    } else {
                        sb.append(line).append(" ");
                        System.out.print("     >> ");
                    }
                }


                switch (sb.toString().toLowerCase()) {
                    case "exit":
                        System.out.println(" Thanks for using HaoSQL");
                        System.exit(0);
                        break;
                    default:
//                        System.out.println("Output " + input);
                        parser.ReInit(new StringReader(sb.toString()));
                        sb = new StringBuilder();
                        statement = parser.init();
                        processStatements(statement);
                        break;
                }
                System.out.println();
            } catch (ParseException e) {
                System.out.println("\nError in parsing the query ");
                e.printStackTrace();
            } catch (NoSuchElementException | IllegalStateException nse) {
                System.out.println("\nInvalid Input. Either choose option 1 or option 2");
            } catch (Exception ex) {
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
                    System.out.println("创建表" + stm.getTableName() + "成功");
                } catch (Exception e) {
                    System.out.println("创建表" + stm.getTableName() + "失败，原因：" + e.getMessage());
                }
                break;
            }
            case DROP: {
                DropStatement stm = (DropStatement) stmt;
                db.dropTable(stm.getTableName());
                System.out.println("删除表" + stm.getTableName() + "成功");
                break;
            }
            case INSERT: {
                InsertStatement stm = (InsertStatement) stmt;
                Table table = db.getTable(stm.getTableName());
                Data data = new Data();
                if (stm.getSelectStatement() == null) {
                    data.columns = BeanUtils.listToColumns(stm.getAttributes());
                    data.data = BeanUtils.llistsToArraylists(stm.getValues());
                    table.insert(data);
                    System.out.println("在表" + table.tableName + "中插入" + data.data.size() + "条数据成功");
                } else {

                }
                break;
            }
            case SELECT: {
                SelectStatement stm = (SelectStatement) stmt;
                Table table = db.getTable(stm.getTables().get(0));
                Column[] columns = BeanUtils.listToColumns(stm.getColumns());
                Data rows = SelectUtils.selectColumn(table.selectAll(), columns);
                List<String> condition = stm.getCondition();

                break;
            }
            case DELETE: {
                DeleteStatement stm = (DeleteStatement) stmt;
                break;
            }
            case UPDATE: {
                UpdateStatement stm = (UpdateStatement) stmt;
                break;
            }

        }
    }



/*
    static void processStatements(ArrayList<Statement> statements, Procedures procedures) throws IOException {
        System.out.println(" Processing input ...");
        CreateTableProc create_proc = new CreateTableProc(procedures.getMem(), procedures.getDisk(),
                procedures.getSchema_manager());
        InsertProc insert_proc = new InsertProc(procedures.getMem(), procedures.getDisk(),
                procedures.getSchema_manager());
        DropTableProc drop_proc = new DropTableProc(procedures.getMem(), procedures.getDisk(),
                procedures.getSchema_manager());
        SelectProc select_proc = new SelectProc(procedures.getMem(), procedures.getDisk(),
                procedures.getSchema_manager());
        DeleteProc delete_proc = new DeleteProc(procedures.getMem(), procedures.getDisk(),
                procedures.getSchema_manager());
        FileOutputStream out = null;

        try {
            out = new FileOutputStream("Result.txt");
            for (Statement stmt : statements) {
                out.write((stmt.toString() + "\r\n").getBytes());
                procedures.getDisk().resetDiskTimer();
                procedures.getDisk().resetDiskIOs();
                long start = System.currentTimeMillis();

                switch (stmt.getType()) {
                    case CREATE:
                        create_proc.createRelation((CreateStatement) stmt, out);
                        break;
                    case INSERT:
                        insert_proc.insertTuples((InsertStatement) stmt, out);
                        break;
                    case DROP:
                        drop_proc.dropRelation((DropStatement) stmt, out);
                        break;
                    case SELECT:
                        select_proc.selectTuples((SelectStatement) stmt, out);
                        break;
                    case DELETE:
                        delete_proc.deleteTuples((DeleteStatement) stmt, out);
                }

                long elapsedTimeMillis = System.currentTimeMillis() - start;
                out.write(("\r\nSystem elapse time = " + elapsedTimeMillis + " ms" + "\r\n").getBytes());
                out.write(("Calculated Disk elapse time = " + procedures.getDisk().getDiskTimer() + " ms" + "\r\n").getBytes());
                out.write(("Calculated Disk I/Os = " + procedures.getDisk().getDiskIOs() + "\r\n").getBytes());
                out.write(("--------------------------------------------------------------------------------------------\r\n").getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                System.out.println("\n Output is logged in file " + System.getProperty("user.dir") + "\\Result.txt");
                out.close();
            }
        }
    }*/
}