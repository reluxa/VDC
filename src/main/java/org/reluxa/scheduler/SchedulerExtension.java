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
import javax.enterprise.inject.spi.Extension;

import org.reluxa.time.TimeServiceIF;
import org.reluxa.vaadin.util.BeanManagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerExtension implements Extension {

  protected Logger log = LoggerFactory.getLogger(this.getClass()); 
	
  ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

  void afterBeanDiscovery(@Observes final AfterBeanDiscovery afterBeanDiscovery, final BeanManager bm) {
    Set<Bean<?>> beans = bm.getBeans(Scheduled.class);
    for (Bean<?> bean : beans) {
      CreationalContext<?> ctx = bm.createCreationalContext(bean);
      Scheduled sched = (Scheduled) bm.getReference(bean, Scheduled.class, ctx);
      log.info(sched.getClass().getName()+" is scheduled at "+ sched.getFirstRun()+" and repeating in "+sched.getPeriodTimeInSeconds() + " seconds");
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
