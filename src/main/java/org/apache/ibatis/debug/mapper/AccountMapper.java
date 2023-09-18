package org.apache.ibatis.debug.mapper;

import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.debug.entity.WalletInfo;

import java.math.BigDecimal;

/**
 * @author Mr.Gao
 * @date 2023/9/12 16:52
 * @apiNote:
 */
@CacheNamespaceRef(value = UserMapper.class)
public interface AccountMapper {


  /**
   * 更新钱包余额
   *
   * @param uid
   * @param amt
   * @return
   */
  @Update("UPDATE account_info SET wallet_amount = #{amt} WHERE uid = #{uid}")
  int updateAccoutWalletAmt(Long uid, BigDecimal amt);


  /**
   * 更新钱包余额
   *
   * @param walletInfo
   * @return
   */
  @Update("UPDATE account_info SET wallet_amount = #{walletAmt} WHERE uid = #{id}")
  int updateWalletAmt(WalletInfo walletInfo);

}
