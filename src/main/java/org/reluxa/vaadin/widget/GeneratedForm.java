package org.reluxa.vaadin.widget;

import java.util.Map;

import org.reluxa.vaadin.util.BeanIntrospector;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.VerticalLayout;

public class GeneratedForm<T> extends VerticalLayout {
	
	/**
	 * Serial Version UID.
	 */
  private static final long serialVersionUID = 1L;

	private Class<T> beanType;
	
	private Class<?> context; 
	
	FormLayout form = new FormLayout();
	
	public GeneratedForm(Class<T> beanType, Class<?> context) {
		addComponent(form);
		this.beanType = beanType;
		this.context = context;
	}
	
	@SuppressWarnings("rawtypes")
  public void setBean(T bean) {
		form.removeAllComponents();
		Map<String, Class<? extends com.vaadin.ui.Field>> fieldTypeMap = BeanIntrospector.getFieldMap(bean.getClass(), context);
		Item item = new BeanItem<T>(bean, BeanIntrospector.getDetailFieldsForView(bean.getClass(), context));
		FieldGroup fieldGroup = new BeanFieldGroup<T>(beanType);
		fieldGroup.setItemDataSource(item);
		fieldGroup.setBuffered(false);
		
		for (final Object propertyId : fieldGroup.getUnboundPropertyIds()) {
			Field<?> field = null;
			if (!Field.class.equals(fieldTypeMap.get(propertyId))) {
				field = fieldGroup.buildAndBind(DefaultFieldFactory.createCaptionByPropertyId(propertyId), propertyId, fieldTypeMap.get(propertyId));
			} else {
				field = fieldGroup.buildAndBind(propertyId);
			}
			if (field instanceof AbstractTextField) {
				((AbstractTextField) field).setNullRepresentation("");
			}
			form.addComponent(field);
		}
	}

}
