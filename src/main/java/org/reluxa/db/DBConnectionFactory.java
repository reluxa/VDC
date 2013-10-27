package org.reluxa.db;

import javax.inject.Singleton;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

@Singleton
public class DBConnectionFactory {

	ObjectContainer db = Db4oEmbedded.openFile("D:\\database.data");

	public ObjectContainer getConnection() {
		return db;
	}
	
}
