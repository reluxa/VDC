package org.reluxa.db;

import java.util.Comparator;

import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.ext.ExtObjectContainer;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import com.db4o.query.QueryComparator;

public class SessionThreadLocalWrapper implements Session {
  
  SessionProducer sp;
  
  public SessionThreadLocalWrapper(SessionProducer sp) {
    this.sp = sp;
  }

  
  public void activate(Object arg0, int arg1) throws Db4oIOException, DatabaseClosedException {
    sp.getCurrentSessionThreadLocal().get().activate(arg0, arg1);
  }


  public boolean close() throws Db4oIOException {
    return sp.getCurrentSessionThreadLocal().get().close();
  }


  public void commit() throws Db4oIOException, DatabaseClosedException, DatabaseReadOnlyException {
    sp.getCurrentSessionThreadLocal().get().commit();
  }


  public void deactivate(Object arg0, int arg1) throws DatabaseClosedException {
    sp.getCurrentSessionThreadLocal().get().deactivate(arg0, arg1);
  }


  public void delete(Object arg0) throws Db4oIOException, DatabaseClosedException, DatabaseReadOnlyException {
    sp.getCurrentSessionThreadLocal().get().delete(arg0);
  }


  public ExtObjectContainer ext() {
    return sp.getCurrentSessionThreadLocal().get().ext();
  }


  public Query query() throws DatabaseClosedException {
    return sp.getCurrentSessionThreadLocal().get().query();
  }


  public <TargetType> ObjectSet<TargetType> query(Class<TargetType> arg0) throws Db4oIOException, DatabaseClosedException {
    return sp.getCurrentSessionThreadLocal().get().query(arg0);
  }


  public <TargetType> ObjectSet<TargetType> query(Predicate<TargetType> arg0, Comparator<TargetType> arg1)
      throws Db4oIOException, DatabaseClosedException {
    return sp.getCurrentSessionThreadLocal().get().query(arg0, arg1);
  }


  public <TargetType> ObjectSet<TargetType> query(Predicate<TargetType> arg0, QueryComparator<TargetType> arg1)
      throws Db4oIOException, DatabaseClosedException {
    return sp.getCurrentSessionThreadLocal().get().query(arg0, arg1);
  }


  public <TargetType> ObjectSet<TargetType> query(Predicate<TargetType> arg0) throws Db4oIOException, DatabaseClosedException {
    return sp.getCurrentSessionThreadLocal().get().query(arg0);
  }


  public <T> ObjectSet<T> queryByExample(Object arg0) throws Db4oIOException, DatabaseClosedException {
    return sp.getCurrentSessionThreadLocal().get().queryByExample(arg0);
  }


  public void rollback() throws Db4oIOException, DatabaseClosedException, DatabaseReadOnlyException {
    sp.getCurrentSessionThreadLocal().get().rollback();
  }


  public void store(Object arg0) throws DatabaseClosedException, DatabaseReadOnlyException {
    sp.getCurrentSessionThreadLocal().get().store(arg0);
  }

  @Override
  public void setRollbackOnly() {
    sp.getCurrentSessionThreadLocal().get().setRollbackOnly();
  }
  
}
