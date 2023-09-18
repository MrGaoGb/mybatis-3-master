package org.apache.ibatis.debug;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.debug.entity.UserInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Gao
 * @date 2023/9/13 16:42
 * @apiNote:
 */
public class TestDbUtils {

  private static final String driverClass = "org.postgresql.Driver";
  private static final String url = "jdbc:postgresql://192.168.1.151:5432/postgres";
  private static final String username = "postgres";
  private static final String password = "12345678qq";

  public static void main(String[] args) {

    // Statement对象
    testQueryByStatement();

    // PreparedStatement对象
    //testQueryByPreparedStatement();

  }

  /**
   * PreparedStatement对象
   */
  public static void testQueryByPreparedStatement() {
    try {
      Class.forName(driverClass);
      Connection connection = DriverManager.getConnection(url, username, password);
      String sql = "SELECT * from user_info where id = ?";
      // PreparedStatement 可以使用占位符
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, 1);
      preparedStatement.execute();

      // 获取结果集
      ResultSet resultSet = preparedStatement.getResultSet();
      List<UserInfo> userInfoList = new ArrayList<>();
      while (resultSet.next()) {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setName(name);
        userInfo.setAge(age);
        userInfoList.add(userInfo);
      }
      // 打印结果集
      System.out.println(JSONObject.toJSONString(userInfoList));

      resultSet.close();
      preparedStatement.close();
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Statement对象
   */
  public static void testQueryByStatement() {
    try {
      Class.forName(driverClass);
      Connection connection = DriverManager.getConnection(url, username, password);
      String sql = "SELECT * from user_info where id = 1";
      // PreparedStatement 可以使用占位符
      Statement statement = connection.createStatement();
      statement.executeQuery(sql);

      // 获取结果集
      ResultSet resultSet = statement.getResultSet();
      List<UserInfo> userInfoList = new ArrayList<>();
      while (resultSet.next()) {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setName(name);
        userInfo.setAge(age);
        userInfoList.add(userInfo);
      }
      // 打印结果集
      System.out.println(JSONObject.toJSONString(userInfoList));

      resultSet.close();
      statement.close();
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
