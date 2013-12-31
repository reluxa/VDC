package org.reluxa.vaadin.widget;

import java.util.Collection;

import org.reluxa.vaadin.util.BeanIntrospector;

import com.vaadin.data.util.BeanItemContainer;

public class CustomBeanItemContainer<BEANTYPE> extends BeanItemContainer<BEANTYPE>{
	
	Class<?> viewName;
	
	public CustomBeanItemContainer(Class<? super BEANTYPE> type, Class<?> viewName) throws IllegalArgumentException {
	  super(type);
	  this.viewName = viewName;
  }
	
	@Override
	public Collection<String> getContainerPropertyIds() {
		return BeanIntrospector.getTableFieldsForView(getBeanType(), viewName);
	}
	
	public void replaceAll(Collection<? extends BEANTYPE> collection) {
		this.removeAllItems();
		this.addAll(collection);
	}
}
