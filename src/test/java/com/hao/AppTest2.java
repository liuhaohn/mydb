package com.hao;

import com.hao.bean.Data;
import com.hao.dao.Column;
import com.hao.dao.DB;
import com.hao.dao.Table;
import com.hao.util.ColumnsBuilder;
import com.hao.util.SelectUtils;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Unit test for simple App.
 */
public class AppTest2 {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void createTest() {
        DB db = new DB();
        db.createTable("new", new Column[]{new Column("c1", "string"), new Column("c2", "int"), new Column("c3", "string"), new Column("c4", "int")});

    }

    @Test
    public void insertTest1() {
        DB db = new DB();
        Table newT = db.getTable("new");
        Data data = new Data();
        data.columns = newT.columns;
        ArrayList<Object[]> objects = new ArrayList<>();
        objects.add(new Object[]{"abc", 12, "ae", 32});
        objects.add(new Object[]{"abc", 12, "ab", 32});
        objects.add(new Object[]{"abd", 12, "ab", 32});
        data.data = objects;
        newT.insert(data);
    }

    @Test
    public void updateTest() {
        DB db = new DB();
        Table newT = db.getTable("new");
        Data update = new Data();
        update.columns = new Column[]{new Column("c2", "int")};
        update.data = new ArrayList<>();
        update.data.add(new String[]{"1123123"});

        Data condition = new Data();
        condition.columns = new Column[]{new Column("c1", "string"), new Column("c3", "string")};
        condition.data = new ArrayList<>();
        condition.data.add(new String[]{"abc", "ab"});
        newT.update(update, condition);
    }

    @Test
    public void deleteTest() {
        DB db = new DB();
        Table newT = db.getTable("new");
        Data update = new Data();
        update.columns = new Column[]{new Column("c2", "int")};
        update.data = new ArrayList<>();
        update.data.add(new String[]{"1123123"});

        Data condition = new Data();
        condition.columns = new Column[]{new Column("c1", "string"), new Column("c3", "string")};
        condition.data = new ArrayList<>();
        condition.data.add(new String[]{"abc", "ab"});
        newT.delete(condition);
    }

    @Test
    public void selectRowTest() {
        DB db = new DB();
        Table newT = db.getTable("new");
        Data data = newT.selectAll();
        Data rowCondition = new Data();
        rowCondition.columns = new ColumnsBuilder().appendColumn("c3").toColumnArray();
        ArrayList<Object[]> data1 = new ArrayList<>();
        data1.add(new String[]{"ab"});
        rowCondition.data = data1;
        Data data2 = SelectUtils.selectRow(data, rowCondition);
        System.out.println(data2);
    }

    @Test
    public void selectColumnTest() {
        DB db = new DB();
        Table newT = db.getTable("new");
        Data data = newT.selectAll();
        Column[] columns = new ColumnsBuilder().appendColumn("c3").toColumnArray();
        Data data2 = SelectUtils.selectColumn(data, columns);

        System.out.println(data2);
    }

    @Test
    public void selectColumnRowTest() {
        DB db = new DB();
        Table newT = db.getTable("new");
        Data data = newT.selectAll();
        Column[] columns = new ColumnsBuilder().appendColumn("c3").appendColumn("c1").appendColumn("c2").appendColumn("c4").toColumnArray();
        Data data2 = SelectUtils.selectColumn(data, columns);

        Data rowCondition = new Data();
        rowCondition.columns = new ColumnsBuilder().appendColumn("c3").toColumnArray();
        ArrayList<Object[]> data1 = new ArrayList<>();
        data1.add(new String[]{"ab"});
        rowCondition.data = data1;
        Data data3 = SelectUtils.selectRow(data2, rowCondition);

        System.out.println(data3);
    }




}
