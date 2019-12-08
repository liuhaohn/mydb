# HaoQL：java实现的SQL子集数据库实例
## 简介
* HaoQL通过javacc生成的解析器，解析SQL语句的子集成Statement对象，
  通过处理程序（Process对象）处理SQL请求。
* 通过Main可以启动服务器，使用telnet连接到服务器即可使用HaoQL。
* HaoQL服务端使用了线程池，支持多telnet客户端并发连接，可以在server.properties中配置线程池数量以及服务端口，
  默认的服务端口为6060，默认的线程数为10，可以在Constants对象中修改默认值。
* HaoQL支持创建、删除表，以及表的增删改查的SQL语句，具体见下一节。
* HaoQL将表单以CSV格式的文件存储在项目目录下，首行是字段名和字段类型，分隔符为\001。

## 使用
1. 运行Main对象（使用ide）
2. 使用telnet连接（windows下命令行中输入：telnet localhost 6060）
3. 当成mysql客户端用即可，支持语句具体如下

## 支持
* 数据定义语句（DML）
    1. create，支持表名，字段名和类型，例如：
'''sql
    create table test(
    c1 str,
    c2 int,
    c3 str,
    c4 int,
    c5 str
    );
'''
    



select c1,c2 from new where c1="abc" and c2=12 or c2=42+10;
insert into test values("123",1,"456",2,"789");
insert into test values("abc",1,"def123",2,"abc.de"),("123",1,"456",2,"789");
insert into test select * from test;

update test set c2 = 1234343 where c1="123";


