package org.reluxa;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.reluxa.db.SessionImpl;
import org.reluxa.db.SessionProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@Log
public class LoggingInterceptor {

	@Inject
	SessionProducer sp;

	@AroundInvoke
	public Object logMethodEntry(InvocationContext ctx) throws Exception {
		int id = getSessionID();
		Logger logger = LoggerFactory.getLogger(ctx.getTarget().getClass());
		logger.debug("sid: " + id + ", " + ctx.getMethod().getName() + "(" + toString(ctx.getParameters()) + ")");
		return ctx.proceed();
	}

	private int getSessionID() {
		int id = -1;
		if (sp.getCurrentSessionThreadLocal() != null && sp.getCurrentSessionThreadLocal().get() != null) {
			id = ((SessionImpl) sp.getCurrentSessionThreadLocal().get()).getId();
		}
		return id;
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
