package cn.fileserver.dao;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;

public class FileDaoTest {

    //入门测试1
    @Test
    public void test() throws Exception {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        Connection connection = DriverManager.getConnection("jdbc:derby:E:/derbttest/testDerby", "", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from test");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        connection.close();
    }


    //入门测试2
    @Test
    public void test1() throws Exception {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection connection = DriverManager.getConnection("jdbc:derby:TESTDB;create=true", "", "");
        Statement statement = connection.createStatement();
        /*statement.execute("create table test(id varchar(20))");
        statement.execute("insert into test values('1')");*/
        ResultSet resultSet = statement.executeQuery("select * from test");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        connection.close();
    }

    //创建数据库
    @Test
    public void testInsert() throws Exception{
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection connection = DriverManager.getConnection("jdbc:derby:File;create=true", "", "");
        Statement statement = connection.createStatement();
        boolean execute = statement.execute("create table tb_file(file_uuid varchar(32) primary key , file_size varchar(30), file_type varchar(10), file_name varchar(100)" +
                ", file_create_time date, file_path varchar(255))");
        System.out.println("execute = " + execute);
        statement.close();
        connection.close();
    }

    //测试插入诗句
    @Test
    public void test3() throws Exception{
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection connection = DriverManager.getConnection("jdbc:derby:File", "", "");
        Statement statement = connection.createStatement();
        //statement.executeUpdate("insert into tb_file values('1','1','1','1','2019-10-17','1')");
        ResultSet resultSet = statement.executeQuery("select * from tb_file");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(5));
        }
        resultSet.close();
        statement.close();
        connection.close();
    }

    //查询数据是否保存成功
    @Test
    public void vertify() throws Exception{
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection connection = DriverManager.getConnection("jdbc:derby:File", "", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from tb_file");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
            System.out.println(resultSet.getString(4));
            System.out.println(resultSet.getString(5));
            System.out.println(resultSet.getString(6));
        }
        resultSet.close();
        statement.close();
        connection.close();
    }

}