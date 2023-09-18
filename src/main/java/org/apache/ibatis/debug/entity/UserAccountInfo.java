package org.apache.ibatis.debug.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Mr.Gao
 * @date 2023/9/12 17:00
 * @apiNote:
 */

public class UserAccountInfo implements Serializable {
  private Long id;

  private String name;

  private Integer age;

  private BigDecimal walletAmount;

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

  public BigDecimal getWalletAmount() {
    return walletAmount;
  }

  public void setWalletAmount(BigDecimal walletAmount) {
    this.walletAmount = walletAmount;
  }

  @Override
  public String toString() {
    return "UserInfo{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", age=" + age +
      ", walletAmount=" + walletAmount +
      '}';
  }
}
