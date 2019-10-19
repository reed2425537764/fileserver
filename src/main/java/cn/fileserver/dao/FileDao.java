package cn.fileserver.dao;

import cn.fileserver.domain.FileData;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

@Slf4j
public class FileDao {

    public void saveData(String uuid, String size, String type, String name, String time, String path) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            //连接数据库
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection("jdbc:derby:File;", "", "");
            //设置sql语句
            statement = connection.prepareStatement("insert into tb_file values(?,?,?,?,?,?)");
            statement.setString(1,uuid);
            statement.setString(2,size);
            statement.setString(3,type);
            statement.setString(4,name);
            statement.setString(5,time);
            statement.setString(6,path);
            //插入数据
            boolean execute = statement.execute();
            //判断是否成功
            if (!execute) {
                log.info("数据插入数据库成功  uuid: {}  path: {}",uuid, path);
            }
            else {
                log.info("数据插入数据库失败  uuid: {}  path: {}",uuid, path);
            }
        } catch (SQLException e) {
            //判断是否成功
            log.info("数据插入数据库失败  uuid: {}  path: {}",uuid, path);
            try {
                Files.delete(Paths.get(path));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //释放资源
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public FileData getDataByUUID(String uuid) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //连接数据库
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection("jdbc:derby:File");
            //设置sql语句
            preparedStatement = connection.prepareStatement("select * from tb_file where file_uuid=?");
            preparedStatement.setString(1,uuid);
            //查询并提取数据
            resultSet = preparedStatement.executeQuery();
            FileData fileData = new FileData();
            while (resultSet.next()) {
                fileData.setUuid(resultSet.getString(1));
                fileData.setSize(resultSet.getString(2));
                fileData.setSuffix(resultSet.getString(3));
                fileData.setName(resultSet.getString(4));
                fileData.setDate(resultSet.getDate(5));
                fileData.setPath(resultSet.getString(6));
            }
            log.info("提取数据成功: {}", fileData.toString());
            //返回数据
            return fileData;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        //释放资源
        finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
