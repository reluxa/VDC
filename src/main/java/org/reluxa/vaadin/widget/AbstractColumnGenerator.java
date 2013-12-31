package org.reluxa.vaadin.widget;

import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public abstract class AbstractColumnGenerator<T,R> implements ColumnGenerator {
	
	@SuppressWarnings("unchecked")
  @Override
	public Object generateCell(Table source, Object itemId, Object columnId) {
	  return generateCell((T)itemId);
	}
	
	public abstract R generateCell(T value);
}
