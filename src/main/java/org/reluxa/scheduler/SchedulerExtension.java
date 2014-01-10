package org.reluxa.scheduler;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

import org.reluxa.time.TimeServiceIF;
import org.reluxa.vaadin.util.BeanManagerUtil;

public class SchedulerExtension implements Extension {

  ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(3);

  public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd, BeanManager bm) {
    bbd.addScope(SchedulerScope.class, false, false);
  }

  void afterBeanDiscovery(@Observes final AfterBeanDiscovery afterBeanDiscovery, final BeanManager bm) {
    afterBeanDiscovery.addContext(new SchedulerScopeContextImpl());

    Set<Bean<?>> beans = bm.getBeans(Scheduled.class);
    for (Bean<?> bean : beans) {
      CreationalContext<?> ctx = bm.createCreationalContext(bean);
      Scheduled sched = (Scheduled) bm.getReference(bean, Scheduled.class, ctx);
      exec.scheduleAtFixedRate(new ScheduledTaskWrapper(sched, bm), calculateInitDelay(sched.getFirstRun(), bm),
	  sched.getPeriodTimeInSeconds(), TimeUnit.SECONDS);
    }
  }

  public long calculateInitDelay(Date date, BeanManager bm) {
    Date now = getTimeService(bm).getCurrentTime();
    return date.getTime() - now.getTime();
  }

  private TimeServiceIF getTimeService(BeanManager bm) {
    return BeanManagerUtil.createBean(TimeServiceIF.class, bm);
  }

}
