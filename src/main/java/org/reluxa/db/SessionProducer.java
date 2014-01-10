package org.reluxa.db;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class SessionProducer {

  private ThreadLocal<Session> currentSession = new ThreadLocal<>();
  
  @Produces @Transacted
  Session makeSession() {
    System.out.println("Session was injected");
    return new SessionThreadLocalWrapper(this);
  }
  
  public ThreadLocal<Session> getCurrentSessionThreadLocal() {
    return currentSession;
  }	
  
  
}
