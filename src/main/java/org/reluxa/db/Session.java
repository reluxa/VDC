package org.reluxa.db;

import com.db4o.ObjectContainer;

public interface Session extends ObjectContainer {
	
	public void setRollbackOnly();

}
