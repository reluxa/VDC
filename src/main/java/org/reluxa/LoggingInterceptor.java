package org.reluxa;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@Log
public class LoggingInterceptor {

  @AroundInvoke
  public Object logMethodEntry(InvocationContext ctx) throws Exception {
    Logger logger = LoggerFactory.getLogger(ctx.getTarget().getClass());
    logger.debug("method called: " + ctx.getMethod().getName() + " params: (" + toString(ctx.getParameters()) + ")");
    return ctx.proceed();
  }

  private String toString(Object[] objs) {
    StringBuilder builder = new StringBuilder();
    if (objs != null) {
      for (int i = 0; i < objs.length; i++) {
	Object object = objs[i];
	builder.append(object);
	if (i < objs.length - 1) {
	  builder.append(",");
	}
      }
    }
    return builder.toString();
  }

}
