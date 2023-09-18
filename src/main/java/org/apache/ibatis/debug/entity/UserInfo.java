package org.apache.ibatis.debug.entity;

import java.io.Serializable;

/**
 * @author Mr.Gao
 * @date 2023/6/16 10:28
 * @apiNote:
 */
public class UserInfo implements Serializable {

  private Long id;

  private String name;

  private Integer age;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "UserInfo{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", age=" + age +
      '}';
  }
}
