package org.reluxa.scheduler;

import javax.enterprise.inject.spi.BeanManager;

import org.reluxa.db.DBConnectionFactory;
import org.reluxa.db.SessionImpl;
import org.reluxa.db.SessionProducer;
import org.reluxa.vaadin.util.BeanManagerUtil;

public class DBTaskWrapper implements Runnable {

	private Runnable delegate;

	private BeanManager bm;

	public DBTaskWrapper(Runnable delegate, BeanManager bm) {
		this.delegate = delegate;
		this.bm = bm;
	}

	@Override
	public void run() {
		boolean hadException = false;
		SessionImpl sessionImpl = new SessionImpl();
		sessionImpl.setConnectionFactory(getDBConnectionFactory());
		SessionProducer sp = getSessionProducer();
		try {
			sessionImpl.init();
			sp.getCurrentSessionThreadLocal().set(sessionImpl);
			delegate.run();
		} catch (Exception ex) {
			ex.printStackTrace();
			hadException = true;
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
	}

	private DBConnectionFactory getDBConnectionFactory() {
		return BeanManagerUtil.createBean(DBConnectionFactory.class, bm);
	}

	private SessionProducer getSessionProducer() {
		return BeanManagerUtil.createBean(SessionProducer.class, bm);
	}

}
