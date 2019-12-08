# HaoQL：java实现的SQL子集数据库实例
## 简介
* HaoQL通过javacc生成的解析器，解析SQL语句的子集成Statement对象，通过处理程序（Process对象）处理SQL请求，通过Main可以启动服务器，使用telnet连接到服务器即可使用HaoQL。
* HaoQL服务端使用了线程池，支持多telnet客户端并发连接，可以在server.properties中配置线程池数量以及服务端口，
  默认的服务端口为6060，默认的线程数为10，可以在Constants对象中修改默认值。
* HaoQL支持创建、删除表，以及表的增删改查的SQL语句，具体见下一节。
* HaoQL将表单以CSV格式的文件存储在项目目录下，首行是字段名和字段类型，分隔符为\001。

## 使用
1. 运行Main对象（使用ide）
2. 使用telnet连接（windows下命令行中输入：telnet localhost 6060）
3. 当成mysql客户端用即可，支持语句具体如下

## 支持
* 数据定义语句（DDL）
    1. `create` 支持指定表名，字段名和类型，例如：
        ```sql
        create table test(
        c1 str,
        c2 int,
        c3 str,
        c4 int,
        c5 str
        );
        ```
    2. `drop` 支持指定表名，例如：
        ```sql
        drop table test;
        ```
* 数据操作语句（DML）        
    1. `insert` 支持指定字段，不指定字段为插入所有字段，可以一次插入多条数据，可以插入子查询数据，例如：
       ```sql
       insert into test (c1,c2) values ("abc",123),("def",456);
       insert into test values ("abc",123,"def",456,"ghi");    
       insert into test select * from test where c1="abc";
       ```
    2. `delete` 支持删除符合条件的行，`where`子句可选，无`where`条件相对于清空表，`where`条件语法参考后文，例如：
        ```sql
       delete from test where c1="abc" and c2=123 or c3="def";
       delete from test;
       ```
    3. `update` 支持一次更新多列，`where`条件可选，例如：
        ```sql
       update test set c1="abc",c2=2 where c1="abc" and c2=123 or c3="def";
       update test set c1="abc";
       ```
    4. `select` 支持*选中所有列和指定列，`where`条件可选，例如：
        ```sql
       select * from test;
       select c1,c2 where c1="abc" and c2=123 or c3="def";
        ```
    5. `where` 支持`and` `or` 两种布尔运算，`and`优先级要高于`or`；仅支持`=`比较运算符，
    左值为字段名，右值为常量，对于数值类型字段，右值可以是包含`+` `-` `*` `/`的数值表达式，例如：
        ```sql
        where c1="abc" and c2 = 2*3+4/5-6 or c3="def"
        ```

