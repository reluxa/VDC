package org.reluxa.db;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter("/*")
public class DBTransactionHandler implements Filter {

	@Inject
	SessionProducer sp;

	@Inject
	DBConnectionFactory connectionFactory;
	
	Logger log = LoggerFactory.getLogger("perf");

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
	    ServletException {
		Long start = System.currentTimeMillis();
		boolean hadException = false;
		SessionImpl sessionImpl = new SessionImpl();
		sessionImpl.setConnectionFactory(connectionFactory);
		try {
			sessionImpl.init();
			sp.getCurrentSessionThreadLocal().set(sessionImpl);
			chain.doFilter(request, response);
		} catch (RuntimeException ex) {
			hadException = true;
			throw ex;
		} finally {
			if (sessionImpl != null) {
				if (hadException || sessionImpl.isRollbackOnly()) {
					sessionImpl.rollback();
				} else {
					sessionImpl.commit();
				}
				sessionImpl.close();
				sp.getCurrentSessionThreadLocal().set(null);
			}
		}
		log.info((System.currentTimeMillis()-start)+"ms");
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
