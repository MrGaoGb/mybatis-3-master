package org.apache.ibatis.debug.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.Statement;

/**
 * @author Mr.Gao
 * @date 2023/9/14 16:49
 * @apiNote:
 */
@Intercepts(
  @Signature(type = StatementHandler.class, method = "update", args = {Statement.class})
)
public class SqlCostInterceptor implements Interceptor {
  @Override
  public Object intercept(Invocation invocation) throws Throwable {

    // 记录SQL语句的开始时间
    long start = System.currentTimeMillis();
    StatementHandler handler = (StatementHandler) invocation.getTarget();

    Object proceed = invocation.proceed();

    long end = System.currentTimeMillis();

    long cost = end - start;

    String sql = handler.getBoundSql().getSql();

    System.out.println("执行SQL:" + sql + ",耗时:" + cost + "ms!");
    return proceed;
  }
}
