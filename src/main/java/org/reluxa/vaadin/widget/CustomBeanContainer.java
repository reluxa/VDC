package org.reluxa.vaadin.widget;

import java.util.Collection;

import org.reluxa.vaadin.util.BeanIntrospector;

import com.vaadin.data.util.BeanContainer;

public class CustomBeanContainer<IDTYPE,BEANTYPE> extends BeanContainer<IDTYPE, BEANTYPE>{

	protected Class<?> viewName;
	
	public CustomBeanContainer(Class<? super BEANTYPE> type, Class<?> viewName) {
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
