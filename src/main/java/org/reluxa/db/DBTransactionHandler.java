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

@WebFilter("/*")
public class DBTransactionHandler implements Filter {

	@Inject
	SessionImpl sessionImpl;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
	    ServletException {
		boolean hadException = false;
		try {
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
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
