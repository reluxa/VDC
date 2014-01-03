package org.reluxa.vaadin.util;

import org.reluxa.vaadin.widget.TableBeanFactory;

import com.vaadin.data.util.BeanItem;

public class TableUtils {
	
	public static <ID> void updateRow(ID id, TableBeanFactory<ID, ?> table) {
		BeanItem<?> bean = table.getItem(id);
		System.out.println(bean);
		for (Object propid : bean.getItemPropertyIds()) {
			// System.out.println("original value+"+bean.getItemProperty(propid).getValue());
			bean.getItemProperty(propid).setValue(
					bean.getItemProperty(propid).getValue());
		}
	}

}
