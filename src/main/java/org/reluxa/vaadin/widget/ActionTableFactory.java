package org.reluxa.vaadin.widget;

import java.util.Collection;

import org.reluxa.vaadin.util.BeanIntrospector;

public class ActionTableFactory<BEANTYPE> extends TableFactory<BEANTYPE> {

	Class<?> actionBean;
	
	public ActionTableFactory(Class<? super BEANTYPE> type, Class<?> viewName, Class<?> actionBean) throws IllegalArgumentException {
	  super(type, viewName);
  }
	
	public Class<?> getTableBeanType() {
		return actionBean;
	}
	
	@Override
	public Collection<String> getContainerPropertyIds() {
		return BeanIntrospector.getTableFieldsForView(getTableBeanType(), viewName);
	}

}
