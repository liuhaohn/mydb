package com.hao;


import com.hao.parser.HaoQLParser;
import com.hao.parser.ParseException;
import com.hao.parser.statement.Statement;

import java.io.StringReader;
import java.util.Scanner;


/*
用于测试解析器是否可以正常运行，将HaoQL解析成Statement

update student set name="a" where age=1
select a,b,c from student where age=3*4+5 order by name
delete from student where age = 3
insert into student(a,b,c) values (1,2,"3"),(3,5,"6")
drop table student
create table student ( a int,b str,c float )

* */
public class ParseTest {
    public static void main(String[] args) throws ParseException {
        while (true)
        test();
    }

    private static void test() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        HaoQLParser pa = new HaoQLParser(new StringReader(scanner.nextLine()));
        Statement statement= pa.init();
        System.out.println();
    }
}
