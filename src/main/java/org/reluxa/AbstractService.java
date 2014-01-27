package org.reluxa;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.reluxa.db.Session;
import org.reluxa.db.Transacted;
import org.reluxa.vaadin.auth.VaadinAccessControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractService {
  
  protected Logger log = LoggerFactory.getLogger(this.getClass()); 

  @Inject
  protected BeanManager beanManager;

  @Inject
  @Transacted
  protected Session db;
  
}
