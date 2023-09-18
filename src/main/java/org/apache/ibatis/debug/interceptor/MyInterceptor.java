package org.apache.ibatis.debug.interceptor;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;

import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * @author Mr.Gao
 * @date 2023/9/13 15:09
 * @apiNote:
 */
@Intercepts(value = {
  /**
   * type:拦截类
   * method:拦截方法
   * args:拦截方法中的参数
   *
   */
  @Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class})
}
)
public class MyInterceptor implements Interceptor {


  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    System.out.println("MyInterceptor#intercept()" + invocation);
    Object target = invocation.getTarget();
    Object[] args = invocation.getArgs();
    PreparedStatement ps = (PreparedStatement) args[0];

    return invocation.proceed();
  }

  @Override
  public Object plugin(Object target) {
    if (target instanceof ParameterHandler) {
      System.out.println("MyInterceptor#plugin()" + target);
      return Plugin.wrap(target, this);
    }
    return target;
  }

  @Override
  public void setProperties(Properties properties) {
    properties.setProperty("k", "v");
  }
}
