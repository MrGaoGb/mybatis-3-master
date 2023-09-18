package org.apache.ibatis.debug.interceptor;

import org.apache.ibatis.debug.entity.WalletInfo;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

/**
 * @author Mr.Gao
 * @date 2023/9/14 16:04
 * @apiNote:
 */
@Intercepts(value = {@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MyInterceptorExecutor implements Interceptor {
  @Override
  public Object intercept(Invocation invocation) throws Throwable {

    Object target = invocation.getTarget();
    Object[] args = invocation.getArgs();
    MappedStatement mappedStatement = (MappedStatement) args[0];
    Object walletInfo = args[1];
    if (walletInfo instanceof WalletInfo) {
      System.out.println("进来修改钱包余额了");
      BoundSql boundSql = mappedStatement.getBoundSql(walletInfo);
      String sql = boundSql.getSql();
      System.out.println("执行SQL:" + sql);
    }
    return invocation.proceed();
  }
}
