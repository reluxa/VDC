package org.reluxa.bid.service;

import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.reluxa.bid.Bid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@BidCache
public class BidCacheInterceptor {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Inject
	BidCacheImpl bidCache;
	
	@AroundInvoke
	public Object lookForInCache(InvocationContext ctx) throws Exception {
		if (ctx.getMethod().getName().equals("getAllBids") && 
				ctx.getMethod().getParameterTypes().length == 1 && 
				ctx.getMethod().getParameterTypes()[0] == Date.class) {
			Object result = bidCache.map.get((Date)ctx.getParameters()[0]);
			if (result != null) {
				log.debug("getAllBids() cache was hit: "+ctx.getParameters()[0]);
				return result;
			} else {
				result = ctx.proceed();
				bidCache.map.put((Date)ctx.getParameters()[0], (Collection<Bid>)result);
				return result;
			}
		}
		return ctx.proceed(); 
	}


}
