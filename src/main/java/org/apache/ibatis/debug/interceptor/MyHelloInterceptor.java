package org.apache.ibatis.debug.interceptor;

import org.apache.ibatis.debug.entity.Hello;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

/**
 * @author Mr.Gao
 * @date 2023/9/14 16:42
 * @apiNote:
 */
@Intercepts(@Signature(type = Hello.class, method = "sayHello", args = String.class))
public class MyHelloInterceptor implements Interceptor {
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    System.out.println("MyHelloInterceptor..." + invocation.getTarget());
    return invocation.proceed();
  }
}
