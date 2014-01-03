package org.reluxa.db;

import javax.inject.Singleton;

import org.reluxa.model.IDObject;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.ConfigScope;
import com.db4o.events.Event4;
import com.db4o.events.EventListener4;
import com.db4o.events.EventRegistryFactory;
import com.db4o.events.ObjectInfoEventArgs;
import com.db4o.internal.Config4Impl;
import com.db4o.internal.config.EmbeddedConfigurationImpl;

@Singleton
public class DBConnectionFactory {

	private ObjectContainer db;

	public DBConnectionFactory() {
		Config4Impl config = new Config4Impl();
		config.generateUUIDs(ConfigScope.GLOBALLY);
		EmbeddedConfigurationImpl embeddedConfig = new EmbeddedConfigurationImpl(config);
		db = Db4oEmbedded.openFile(embeddedConfig,"D:\\database.data");
		
		EventRegistryFactory.forObjectContainer(db).created().addListener(new EventListener4<ObjectInfoEventArgs>() {
			@Override
			public void onEvent(Event4<ObjectInfoEventArgs> arg0, ObjectInfoEventArgs arg1) {
				if (arg1.object() instanceof IDObject) {
					((IDObject)arg1.object()).setId(arg1.info().getInternalID());
				}
				System.out.println("Created: "+arg1.object());

			}
		});
		
		EventRegistryFactory.forObjectContainer(db).activated().addListener(new EventListener4<ObjectInfoEventArgs>() {
			@Override
			public void onEvent(Event4<ObjectInfoEventArgs> arg0, ObjectInfoEventArgs arg1) {
				if (arg1.object() instanceof IDObject) {
					((IDObject)arg1.object()).setId(arg1.info().getInternalID());
				}
			}
		});

	}

	public ObjectContainer getConnection() {
		return db;
	}

}
