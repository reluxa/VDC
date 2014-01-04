package org.reluxa.vaadin.util;

import java.lang.reflect.Field;
import java.util.HashSet;

import org.reluxa.model.IDObject;

import com.db4o.ObjectContainer;

public class RequirsiveActivator {

	private static void activate(IDObject node, ObjectContainer container, HashSet<Integer> ids) throws Exception {
		System.out.println("Binding: "+node);

		ids.add(System.identityHashCode(node));
		if (node != null && node.getId() > 0) {
			container.ext().bind(node, node.getId());	
		}
		
		Field[] fields = node.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object val = field.get(node);
	    if (!ids.contains(System.identityHashCode(val)) && val instanceof IDObject) {
	    	activate((IDObject)val, container, ids);
	    }
    }
	}
	
	public static void activate(IDObject node, ObjectContainer container) {
		try {
	    activate(node, container, new HashSet<Integer>());
    } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
	}
}
