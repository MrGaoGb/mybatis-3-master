package org.apache.ibatis.debug;

import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.debug.entity.SessionInfo;
import org.apache.ibatis.debug.entity.WalletInfo;
import org.apache.ibatis.debug.mapper.AccountMapper;
import org.apache.ibatis.debug.mapper.SessionMapper;
import org.apache.ibatis.debug.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Mr.Gao
 * @date 2023/6/16 10:20
 * @apiNote:
 */
public class TestMain {

  public static void main(String[] args) throws IOException {
    // 加载mybatis配置
    InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
    // 创建SqlSession工厂
    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    // 同一个会话中，三次查询结果
    //createSingleSessionAllDoQuery(sessionFactory);

    // 模拟在同一个会话中 先执行查询、后更新、最后再执行查询
    //createSingleSession(sessionFactory);

    // 创建两个会话，会话1执行数据查询，会话2执行数据更新，会话1再执行数据查询
    //createTwoSession(sessionFactory);

    // 创建两个会话，会话1以相同的SQL查询两次，会话2以相同的SQL查询一次 判断两个会话之间是否存在影响
    //createTwoSessionByTwoLevel(sessionFactory);

    // 创建两个会话，会话1执行一次查询并提交事务，然后会话1再以相同的SQL查询，接着会话2再以相同的SQL查询
    //createTwoSessionByTwoLevelByTransactionCommit(sessionFactory);

    //  创建两个会话，会话1执行一次并提交事务，会话2执行一次更新并提交事务，接着会话1再执行一次相同的查询
    //createTwoSessionByTwoLevelByTransactionReadCommit(sessionFactory);

    // 多表关联查询，会话1先多表关联查询一次,会话2执行数据更新一次，然后会话1再以多表关联查一次
    //createTwoSessionByTwoLevelByTransactionReadCommitAndUnion(sessionFactory);

    // 根据对象入参执行更新SQL
    //updateUserInfoByEntity(sessionFactory);

    // 添加用户会话信息
    //addUserSessionInfo(sessionFactory);

    //使用SelectProvider注解查询列表
    queryAllUserSessionInfoBySelectProvider(sessionFactory);
  }

  /**
   * 同一个会话中，三次查询结果
   *
   * @param sessionFactory
   */
  private static void createSingleSessionAllDoQuery(SqlSessionFactory sessionFactory) {
    // 获取SqlSession对象
    SqlSession sqlSession = sessionFactory.openSession();
    // 获取Mapper对象
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    // 一级缓存机制展示
    System.out.println("用户信息:" + JSONObject.toJSONString(userMapper.selectUserById(1L)));
    System.out.println("用户信息:" + JSONObject.toJSONString(userMapper.selectUserById(1L)));
    System.out.println("用户信息:" + JSONObject.toJSONString(userMapper.selectUserById(1L)));
  }

  /**
   * 模拟在同一个会话中 先执行查询、后更新、最后再执行查询
   *
   * @param sessionFactory
   */
  private static void createSingleSession(SqlSessionFactory sessionFactory) {
    // 获取SqlSession对象
    SqlSession sqlSession = sessionFactory.openSession();
    // 获取Mapper对象
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    System.out.println("用户信息(前):" + JSONObject.toJSONString(userMapper.selectUserById(1L)));
    int effRows = userMapper.updateUserAgeById(35, 1L);
    System.out.println(effRows == 1 ? "修改成功!" : "修改失败!");
    System.out.println("用户信息(后):" + JSONObject.toJSONString(userMapper.selectUserById(1L)));
  }


  /**
   * 创建两个会话，会话1执行数据查询，会话2执行数据更新，会话1再执行数据查询
   *
   * @param sessionFactory
   */
  private static void createTwoSession(SqlSessionFactory sessionFactory) {
    /**
     * true:表示事务自动提交
     */
    SqlSession sqlSession1 = sessionFactory.openSession(true);
    SqlSession sqlSession2 = sessionFactory.openSession(true);

    UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
    UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

    // 会话1
    System.out.println("用户信息(会话1):" + JSONObject.toJSONString(userMapper1.selectUserById(1L)));

    // 会话2
    int effRows = userMapper2.updateUserAgeById(35, 1L);
    System.out.println(effRows == 1 ? "(会话2)修改成功!" : "(会话2)修改失败!");

    // 会话1
    System.out.println("用户信息(会话1):" + JSONObject.toJSONString(userMapper1.selectUserById(1L)));
  }

  /**
   * 创建两个会话，会话1以相同的SQL查询两次，会话2以相同的SQL查询一次
   *
   * @param sessionFactory
   */
  private static void createTwoSessionByTwoLevel(SqlSessionFactory sessionFactory) {
    SqlSession sqlSession1 = sessionFactory.openSession(false);
    SqlSession sqlSession2 = sessionFactory.openSession(false);

    UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
    UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

    // 会话1
    System.out.println("用户信息(会话1):" + JSONObject.toJSONString(userMapper1.selectUserById(1L)));
    System.out.println("用户信息(会话1):" + JSONObject.toJSONString(userMapper1.selectUserById(1L)));

    // 会话2
    System.out.println("用户信息(会话2):" + JSONObject.toJSONString(userMapper2.selectUserById(1L)));
  }

  /**
   * 创建两个会话，会话1执行一次查询并提交事务，然后会话1再以相同的SQL查询，接着会话2再以相同的SQL查询
   *
   * @param sessionFactory
   */
  private static void createTwoSessionByTwoLevelByTransactionCommit(SqlSessionFactory sessionFactory) {
    SqlSession sqlSession1 = sessionFactory.openSession(false);
    SqlSession sqlSession2 = sessionFactory.openSession(false);

    UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
    UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

    // 会话1
    System.out.println("用户信息(会话1):" + JSONObject.toJSONString(userMapper1.selectUserById(1L)));

    // 手动提交事务
    sqlSession1.commit();
    System.out.println("用户信息(会话1):" + JSONObject.toJSONString(userMapper1.selectUserById(1L)));

    // 会话2
    System.out.println("用户信息(会话2):" + JSONObject.toJSONString(userMapper2.selectUserById(1L)));
  }

  /**
   * 创建两个会话，会话1执行一次并提交事务，会话2执行一次更新并提交事务，接着会话1再执行一次相同的查询
   *
   * @param sessionFactory
   */
  private static void createTwoSessionByTwoLevelByTransactionReadCommit(SqlSessionFactory sessionFactory) {
    // 将事务隔离级别设置为读已提交
    SqlSession sqlSession1 = sessionFactory.openSession(TransactionIsolationLevel.READ_COMMITTED);
    SqlSession sqlSession2 = sessionFactory.openSession(TransactionIsolationLevel.READ_COMMITTED);

    UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
    UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

    // 会话1
    System.out.println("用户信息(会话1):" + JSONObject.toJSONString(userMapper1.selectUserById(1L)));
    // 手动提交事务
    sqlSession1.commit();

    // 会话2
    userMapper2.updateUserAgeById(33, 1L);
    sqlSession2.commit();
    System.out.println("用户信息(会话2): 更新用户年龄信息!");

    // 会话1
    System.out.println("用户信息(会话1):" + JSONObject.toJSONString(userMapper2.selectUserById(1L)));
  }

  /**
   * 多表关联查询，会话1先多表关联查询一次,会话2执行数据更新一次，然后会话1再以多表关联查一次
   *
   * @param sessionFactory
   */
  private static void createTwoSessionByTwoLevelByTransactionReadCommitAndUnion(SqlSessionFactory sessionFactory) {
    // 将事务隔离级别设置为读已提交
    SqlSession sqlSession1 = sessionFactory.openSession(TransactionIsolationLevel.READ_COMMITTED);
    SqlSession sqlSession2 = sessionFactory.openSession(TransactionIsolationLevel.READ_COMMITTED);

    UserMapper userMapper = sqlSession1.getMapper(UserMapper.class);
    AccountMapper accountMapper = sqlSession2.getMapper(AccountMapper.class);

    // 会话1
    System.out.println("用户信息(会话1):" + JSONObject.toJSONString(userMapper.selectUsersByUnionAccountInfo()));
    // 手动提交事务
    sqlSession1.commit();

    // 会话2
    accountMapper.updateAccoutWalletAmt(2L, new BigDecimal("99999999"));
    sqlSession2.commit();
    System.out.println("用户信息(会话2): 更新账户余额信息!");

    // 会话1
    System.out.println("用户信息(会话1):" + JSONObject.toJSONString(userMapper.selectUsersByUnionAccountInfo()));
  }


  /**
   * 用户更新根据入参类型为对象
   *
   * @param sessionFactory
   */
  private static void updateUserInfoByEntity(SqlSessionFactory sessionFactory) {
    SqlSession sqlSession = sessionFactory.openSession();
    AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
    WalletInfo walletInfo = new WalletInfo();
    walletInfo.setId(1L);
    walletInfo.setWalletAmt(new BigDecimal("1000"));
    accountMapper.updateWalletAmt(walletInfo);
    System.out.println("更新账户余额信息成功!");
  }

  /**
   * 添加用户会话信息
   *
   * @param sessionFactory
   */
  private static void addUserSessionInfo(SqlSessionFactory sessionFactory) {
    SqlSession sqlSession = sessionFactory.openSession();
    SessionMapper sessionMapper = sqlSession.getMapper(SessionMapper.class);
    SessionInfo sessionInfo = new SessionInfo();
    sessionInfo.setId(2L);
    sessionInfo.setSessionId(UUID.randomUUID().toString());
    sessionInfo.setCreateTime(new Date());
    sessionInfo.setNickName("飞天1号");
    sessionMapper.addSessionInfo(sessionInfo);
    System.out.println("新增用户会话信息!");
    // 提交事务
    sqlSession.commit();
  }

  /**
   * 使用SelectProvider注解查询列表
   *
   * @param sessionFactory
   */
  private static void queryAllUserSessionInfoBySelectProvider(SqlSessionFactory sessionFactory) {
    SqlSession sqlSession = sessionFactory.openSession();
    SessionMapper sessionMapper = sqlSession.getMapper(SessionMapper.class);
    List<SessionInfo> sessionInfoList = sessionMapper.selectAll("嫦娥");
    System.out.println(JSONObject.toJSONString(sessionInfoList));
    // 提交事务
    sqlSession.commit();
  }
}
