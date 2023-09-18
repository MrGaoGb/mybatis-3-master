package org.apache.ibatis.debug.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.debug.entity.SessionInfo;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @author Mr.Gao
 * @date 2023/9/15 11:32
 * @apiNote:
 */
public interface SessionMapper {

  /**
   * 根据用户id获取会话信息
   *
   * @param id
   * @return
   */
  @Select("SELECT * from session_info WHERE id = #{id}")
  SessionInfo getById(Long id);

  /**
   * 查询所有会话信息
   *
   * @return
   */
  //@Select("SELECT * from session_info")
  @SelectProvider(type = UseSqlBuilder.class, method = "buildQueryCondition")
  List<SessionInfo> selectAll(String name);

  /**
   * 更新用户信息
   *
   * @param sessionInfo
   * @return
   */
  @Update("UPDATE session_info SET session_id = #{sessionId} WHERE id = #{id}")
  int updateSessionInfo(SessionInfo sessionInfo);

  /**
   * 新增会话信息
   *
   * @param sessionInfo
   * @return
   */
  @Insert("INSERT INTO session_info(id,session_id,nick_name,create_time) VALUES (#{id},#{sessionId},#{nickName},#{createTime})")
  int addSessionInfo(SessionInfo sessionInfo);


  public class UseSqlBuilder {

    public String buildQueryCondition(String name) {

      return new SQL() {
        {
          SELECT("*");
          FROM("session_info");
          if (name != null) {
            WHERE("nick_name like CONCAT('%',#{name},'%')");
          }
        }
      }.toString();
    }


  }
}
