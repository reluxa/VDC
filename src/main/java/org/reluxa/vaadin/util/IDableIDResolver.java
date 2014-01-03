package org.reluxa.vaadin.util;

import org.reluxa.model.IDObject;

import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;

public class IDableIDResolver implements BeanIdResolver<Long, IDObject> {

	@Override
	public Long getIdForBean(IDObject bean) {
		return bean.getId();
	}

}
