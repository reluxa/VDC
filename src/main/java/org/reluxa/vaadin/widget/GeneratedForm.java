package org.reluxa.vaadin.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.reluxa.vaadin.annotation.Detail;
import org.reluxa.vaadin.util.BeanIntrospector;
import org.reluxa.vaadin.util.StringUtil;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.VerticalLayout;

public class GeneratedForm<T> extends VerticalLayout implements ValueChangeListener {
	
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	public interface FormChangeListener {
		void formChanged();
	}
	
	List<FormChangeListener> listeners = new ArrayList<>();

	/**
	 * Serial Version UID.
	 */
  private static final long serialVersionUID = 1L;

	private Class<T> beanType;
	
	private Class<?> context; 
	
	FormLayout form = new FormLayout();
	
	BeanFieldGroup<T> fieldGroup;
	
	public GeneratedForm(Class<T> beanType, Class<?> context) {
		addComponent(form);
		this.beanType = beanType;
		this.context = context;
		fieldGroup = new BeanFieldGroup<T>(beanType);
	}
	
	@SuppressWarnings("rawtypes")
  public void setBean(final T bean) {
		form.removeAllComponents();
		if (bean == null) {
			return;
		}
		Map<String, Detail> details = BeanIntrospector.getDetailsForFeild(bean.getClass(), context);
		Item item = new BeanItem<T>(bean, BeanIntrospector.getDetailFieldsForView(bean.getClass(), context));
		fieldGroup = new BeanFieldGroup<T>(beanType);
		fieldGroup.setItemDataSource(item);
		fieldGroup.setBuffered(false);
		for (final Object propertyId : fieldGroup.getUnboundPropertyIds()) {
			Field<?> field = null;
			if (!Field.class.equals(details.get(propertyId).type())) {
				try {
	        field = details.get(propertyId).type().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
				fieldGroup.bind(field, propertyId);
			} else {
				field = fieldGroup.buildAndBind(propertyId);
			}
			if (field instanceof AbstractTextField) {
				((AbstractTextField) field).setNullRepresentation("");
			}
			
			field.setCaption(createFieldCaption(propertyId.toString(), details));
			field.setId(propertyId.toString());
			field.addValueChangeListener(this);
			form.addComponent(field);
		}
	}
	
	
	private String createFieldCaption(String fieldName, Map<String, Detail> details) {
		if (StringUtils.EMPTY.equals(details.get(fieldName).propertyName())) {
			return StringUtil.fieldToSentenceCase(fieldName);
		} else {
			return details.get(fieldName).propertyName();
		}
	}

	
	public boolean isValid() {
		T bean = fieldGroup.getItemDataSource().getBean();
	  return fieldGroup.isValid() && validator.validate(bean).size() == 0;
	}

	@Override
  public void valueChange(ValueChangeEvent event) {
		for (FormChangeListener listener : listeners) {
	    listener.formChanged();
    }
  }
	
	public void addFormChangeListener(FormChangeListener listener) {
		listeners.add(listener);
	}
	
	public void removeFormChangeListener(FormChangeListener listener) {
		listeners.remove(listener);
	}

}
