package org.apache.ibatis.debug.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Mr.Gao
 * @date 2023/9/14 15:56
 * @apiNote:
 */
public class WalletInfo implements Serializable {

  private Long id;

  private BigDecimal walletAmt;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getWalletAmt() {
    return walletAmt;
  }

  public void setWalletAmt(BigDecimal walletAmt) {
    this.walletAmt = walletAmt;
  }
}
