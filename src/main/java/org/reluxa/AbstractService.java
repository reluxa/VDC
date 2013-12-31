package org.reluxa;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.reluxa.db.Session;

public class AbstractService {
	
	@Inject 
	protected BeanManager beanManager;

	@Inject
	protected Session db;

}
