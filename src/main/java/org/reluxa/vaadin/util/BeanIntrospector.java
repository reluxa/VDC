package org.reluxa.vaadin.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.reluxa.vaadin.annotation.Detail;
import org.reluxa.vaadin.annotation.GUI;
import org.reluxa.vaadin.annotation.Table;

import com.google.common.collect.TreeMultimap;

public class BeanIntrospector {

	public interface DetailMatcher {
		void found(Detail detail, Field field);
	}
	
	public interface TableMatcher {
		void found(Table table, Field field);
	}

	public static Collection<String> getDetailFields(Class<?> clazz) {
		TreeMultimap<Integer, String> map = TreeMultimap.create();
		try {
			BeanInfo info = Introspector.getBeanInfo(clazz);
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				Detail prop = field.getAnnotation(Detail.class);
				if (prop != null && validProperty(info, field.getName())) {
					map.put(prop.order(), field.getName());
				}
			}
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map.values();
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, Class<? extends com.vaadin.ui.Field>> getFieldMap(Class<?> clazz, Class<?> forView) {
		final Map<String, Class<? extends com.vaadin.ui.Field>> result = new HashMap<>();
		visit(clazz, forView, new DetailMatcher() {
			@Override
			public void found(Detail detail, Field field) {
				result.put(field.getName(), detail.type());
			}
		});
		return result;
	}

	public static Collection<String> getDetailFieldsForView(Class<?> clazz, Class<?> forView) {
		final TreeMultimap<Integer, String> map = TreeMultimap.create();
		visit(clazz, forView, new DetailMatcher() {
			@Override
			public void found(Detail detail, Field field) {
				map.put(detail.order(), field.getName());
			}
		});
		return map.values();
	}
	
	
	public static Collection<String> getTableFieldsForView(Class<?> clazz, Class<?> forView) {
		final TreeMultimap<Integer, String> map = TreeMultimap.create();
		visit(clazz, forView, new TableMatcher() {
			@Override
			public void found(Table table, Field field) {
				map.put(table.order(), field.getName());
			}
		});
		return map.values();
	}

	private static void visit(Class<?> clazz, Class<?> forView, DetailMatcher matcher) {
		try {
			BeanInfo info = Introspector.getBeanInfo(clazz);
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {

				GUI gui = field.getAnnotation(GUI.class);
				if (gui != null) {
					for (Detail prop : gui.detail()) {
						if (prop != null && forView.equals(prop.context()) && validProperty(info, field.getName())) {
							matcher.found(prop, field);
						}
					}
				}
			}
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static void visit(Class<?> clazz, Class<?> forView, TableMatcher matcher) {
		try {
			BeanInfo info = Introspector.getBeanInfo(clazz);
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {

				GUI gui = field.getAnnotation(GUI.class);
				if (gui != null) {
					for (Table prop : gui.table()) {
						if (prop != null && forView.equals(prop.context()) && validProperty(info, field.getName())) {
							matcher.found(prop, field);
						}
					}
				}
			}
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static boolean validProperty(BeanInfo info, String name) {
		for (PropertyDescriptor desc : info.getPropertyDescriptors()) {
			if (name.equals(desc.getName())) {
				return true;
			}
		}
		return false;
	}

}
