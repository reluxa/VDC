package org.reluxa.scheduler;

import java.lang.annotation.Annotation;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

import org.reluxa.scheduler.SchedulerScopeContext.ScopedInstance;
import org.reluxa.scheduler.SchedulerScopeContext.ThreadLocalState;

public class SchedulerScopeContextImpl implements Context {

	public Class<? extends Annotation> getScope() {
		return SchedulerScope.class;
	}

	public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
		Bean bean = (Bean) contextual;
		ScopedInstance si = null;
		ThreadLocalState tscope = SchedulerScopeContext.state.get();
//		if (Foo.class.isAssignableFrom(bean.getBeanClass())) {
//			si = tscope.fooInstances.get(id);
//			if (si == null) {
//				si = new ScopedInstance();
//				si.bean = bean;
//				si.ctx = creationalContext;
//				si.instance = bean.create(creationalContext);
//				tscope.fooInstances.put(id, si);
//				tscope.allInstances.add(si);
//			}
//
//			return (T) si.instance;
//		} else {
//			si = new ScopedInstance();
//			si.bean = bean;
//			si.ctx = creationalContext;
//			si.instance = bean.create(creationalContext);
//			tscope.allInstances.add(si);
//		}
		return (T)si.instance;
	}

	public <T> T get(Contextual<T> contextual) {
		throw new IllegalArgumentException();
	}

	public boolean isActive() {
	  	return true;
		//return SchedulerScopeContext.state.get() != null ? true : false;
	}

}