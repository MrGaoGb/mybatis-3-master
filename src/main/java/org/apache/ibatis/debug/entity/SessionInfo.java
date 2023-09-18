package org.apache.ibatis.debug.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Mr.Gao
 * @date 2023/9/15 11:32
 * @apiNote:
 */
public class SessionInfo implements Serializable {

  private Long id;

  private String sessionId;

  /**
   * 昵称
   */
  private String nickName;

  private Date createTime;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }
}
