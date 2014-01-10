package org.reluxa.scheduler;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

@Dependent
public class SchedulerScopeContext {

	ThreadLocalState scope = null;

	public static ThreadLocal<ThreadLocalState> state = new ThreadLocal<ThreadLocalState>();

	static class ThreadLocalState {
		Set<ScopedInstance<?>> allInstances = new HashSet<ScopedInstance<?>>();
	}

	@Inject
	BeanManager bm;

	public void create() {
		scope = new ThreadLocalState();
	}

	public void begin() {
		if (state.get() != null) {
			throw new IllegalAccessError("Already in FooScope");
		}
		state.set(scope);
	}

	public void end() {
		if (state.get() == null) {
			throw new IllegalAccessError("Not in FooScope");
		}
		state.remove();
	}

	public <T> T newInstance(Class<T> clazz) {
		Set<Bean<?>> beans = bm.getBeans(clazz, new AnnotationLiteral<Any>() {
		});
		if (beans.size() > 0) {
			Bean bean = beans.iterator().next();
			CreationalContext cc = bm.createCreationalContext(bean);
			return (T) bm.getReference(bean, clazz, cc);
		}
		return null;
	}

	public void destroy() {
		//Since this is not a CDI NormalScope we are responsible for managing the entire lifecycle, including
		//destroying the beans
		for (ScopedInstance entry2 : scope.allInstances) {
			entry2.bean.destroy(entry2.instance, entry2.ctx);
		}
		scope = null;
	}

	public static class ScopedInstance<T> {
		Bean<T> bean;
		CreationalContext<T> ctx;
		T instance;
	}

}
