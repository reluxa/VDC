package org.reluxa.vaadin.widget;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public abstract class AbstractColumnGenerator<T,R> implements ColumnGenerator {
	
	@SuppressWarnings("unchecked")
	@Override
	public Object generateCell(Table source, Object itemId, Object columnId) {
	  BeanItem<T> bean = (BeanItem<T>)source.getItem(itemId);
	  return generateCell(bean.getBean());
	}
	
	public abstract R generateCell(T value);
}
