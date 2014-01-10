package org.reluxa;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.reluxa.db.Session;
import org.reluxa.db.Transacted;

public class AbstractService {
	
	@Inject 
	protected BeanManager beanManager;

	@Inject @Transacted
	protected Session db;

}
