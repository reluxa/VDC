package org.reluxa.vaadin.util;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class BeanManagerUtil {

  public static BeanManager lookup() {
    BeanManager result = null;
    String name = "java:comp/" + BeanManager.class.getSimpleName();
    InitialContext ic;
    try {
      ic = new InitialContext();
      result = (BeanManager) ic.lookup(name);
    } catch (NamingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }

  public static <T> T createBean(Class<T> beanClass) {
    BeanManager bm = lookup();
    return createBean(beanClass, bm);
  }

  public static <T> T createBean(Class<T> beanClass, BeanManager bm) {
    Set<Bean<?>> beans = bm.getBeans(beanClass);
    if (beans.size() == 0) {
      throw new RuntimeException("Could not create bean:" + beanClass.getName());
    }
    Bean bean = beans.iterator().next();
    CreationalContext cc = bm.createCreationalContext(bean);
    return (T) bm.getReference(bean, beanClass, cc);
  }

}
