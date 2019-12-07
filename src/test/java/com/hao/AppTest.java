package com.hao;

import com.hao.dao.Column;
import com.hao.dao.DB;
import com.hao.bean.Data;
import com.hao.dao.Table;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void createTest() {
        DB db = new DB();
        db.createTable("newT", new Column[]{new Column("c1", "string"), new Column("c2", "int")});

    }

    @Test
    public void insertTest1() {
        DB db = new DB();
        Table newT = db.getTable("newT");
        Data data = new Data();
        data.columns = newT.columns;
        ArrayList<Object[]> objects = new ArrayList<>();
        objects.add(new Object[]{"12", 12});
        objects.add(new Object[]{"123", 3});
        objects.add(new Object[]{"124", 21});
        data.data = objects;
        newT.insert(data);
    }

    @Test
    public void insertTest2() {
        DB db = new DB();
        Table newT = db.getTable("newT");
        Data data = new Data();
        data.columns = new Column[]{new Column("c2", "int")};
        ArrayList<Object[]> objects = new ArrayList<>();
        objects.add(new Object[]{ 12});
        objects.add(new Object[]{3});
        objects.add(new Object[]{21});
        data.data = objects;
        newT.insert(data);
    }

    @Test
    public void truncateTest() {
        DB db = new DB();
        Table newT = db.getTable("newT");
        newT.truncate();
    }

    @Test
    public void updateTest() {
        DB db = new DB();
        Table newT = db.getTable("newT");
        insertTest1();
        Data update = new Data();
        update.columns = new Column[]{new Column("c2", "int")};
        update.data = new ArrayList<>();
        update.data.add(new String[]{"123"});

        Data condition = new Data();
        condition.columns=new Column[]{new Column("c1","string")};
        condition.data=new ArrayList<>();
        condition.data.add(new String[]{"12"});
        newT.update(update,condition);
    }


}
