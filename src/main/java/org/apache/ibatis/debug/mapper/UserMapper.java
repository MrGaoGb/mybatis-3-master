package org.apache.ibatis.debug.mapper;


import org.apache.ibatis.annotations.*;
import org.apache.ibatis.debug.entity.UserAccountInfo;
import org.apache.ibatis.debug.entity.UserInfo;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author Mr.Gao
 * @date 2023/6/16 10:27
 * @apiNote:
 */
@CacheNamespace
public interface UserMapper {

  /**
   * 根据用户id获取用户信息
   *
   * @param id
   * @return
   */
  @Results(
    value = {@Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
      @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR)}
  )
  @Options
  @Select("SELECT * from user_info where id = #{id}")
  UserInfo selectUserById(@Param("id") Long id);

  /**
   * 获取钱包余额信息
   *
   * @return
   */
  @Options
  @Select("SELECT\n" +
    "      u.id,\n" +
    "      u.name,\n" +
    "      u.age,\n" +
    "      acc.wallet_amount\n" +
    "    from user_info AS u\n" +
    "    LEFT JOIN account_info AS acc\n" +
    "    ON u.id = acc.uid")
  List<UserAccountInfo> selectUsersByUnionAccountInfo();

  /**
   * 查询所有的用户列表
   *
   * @return
   */
  @Options
  @Select("SELECT * from user_info")
  List<UserInfo> selectAll();

  /**
   * 根据用户主键更改年龄
   *
   * @param age
   * @param id
   * @return
   */
  @Options
  @Update("UPDATE user_info SET age = #{age} WHERE id = #{id}")
  int updateUserAgeById(@Param("age") Integer age, @Param("id") Long id);
}
