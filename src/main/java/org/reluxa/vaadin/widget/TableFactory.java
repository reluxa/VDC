package org.reluxa.vaadin.widget;

import java.util.Map;

import org.reluxa.vaadin.util.BeanIntrospector;

import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public class TableFactory<BEANTYPE> extends CustomBeanItemContainer<BEANTYPE>{
	
	public TableFactory(Class<? super BEANTYPE> type, Class<?> viewName) throws IllegalArgumentException {
	  super(type,viewName);
  }
	
	public Class<?> getTableBeanType() {
		return getBeanType();
	}
	
	public Table createTable() {
		Table table = new Table(null, this);
		Map<String, Class<? extends ColumnGenerator>> map = BeanIntrospector.getGeneratorFieldsForTable(getTableBeanType(), viewName);
		for (String field: map.keySet()) {
			Class<? extends ColumnGenerator> clazz = map.get(field);
			if (!ColumnGenerator.class.equals(clazz)) {
				try {
	        table.addGeneratedColumn(field,clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
			}
    }
		return table;
	}
	
	


}
