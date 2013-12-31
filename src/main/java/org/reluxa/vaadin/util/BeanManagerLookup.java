package org.reluxa.vaadin.util;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class BeanManagerLookup {

	public static BeanManager lookup() {
		BeanManager result = null;
  	String name = "java:comp/" + BeanManager.class.getSimpleName();
  	InitialContext ic;
    try {
	    ic = new InitialContext();  	
	    result = (BeanManager) ic.lookup(name);
    } catch (NamingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
    return result;
	}
}
