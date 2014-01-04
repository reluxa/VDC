package org.reluxa.db;

import java.util.Comparator;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.reluxa.model.IDObject;
import org.reluxa.vaadin.util.RequirsiveActivator;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.ext.ExtObjectContainer;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import com.db4o.query.QueryComparator;

/**
 * http://community.versant.com/documentation/reference/db4o-8.0/java/reference/Content/platform_specific_issues/web/servlets.htm
 * @author U299873
 */
@RequestScoped
public class SessionImpl implements Session {

	@Inject
	DBConnectionFactory connectionFactory;

	private static int counter = 0;;

	private boolean rollbackOnly;

	private ObjectContainer delegate;

	private int id;

	@PostConstruct
	public void init() {
		delegate = connectionFactory.getConnection().ext().openSession();
		id = counter++;
	}

	public void activate(Object arg0, int arg1) throws Db4oIOException, DatabaseClosedException {
		delegate.activate(arg0, arg1);
	}

	public boolean close() throws Db4oIOException {
		return delegate.close();
	}

	public void commit() throws Db4oIOException, DatabaseClosedException, DatabaseReadOnlyException {
		delegate.commit();
	}

	public void deactivate(Object arg0, int arg1) throws DatabaseClosedException {
		delegate.deactivate(arg0, arg1);
	}

	public void delete(Object arg0) throws Db4oIOException, DatabaseClosedException, DatabaseReadOnlyException {
		delegate.delete(arg0);
	}

	public ExtObjectContainer ext() {
		return delegate.ext();
	}

	public Query query() throws DatabaseClosedException {
		return delegate.query();
	}

	public <TargetType> ObjectSet<TargetType> query(Class<TargetType> arg0) throws Db4oIOException,
	    DatabaseClosedException {
		return delegate.query(arg0);
	}

	public <TargetType> ObjectSet<TargetType> query(Predicate<TargetType> arg0, Comparator<TargetType> arg1)
	    throws Db4oIOException, DatabaseClosedException {
		return delegate.query(arg0, arg1);
	}

	public <TargetType> ObjectSet<TargetType> query(Predicate<TargetType> arg0, QueryComparator<TargetType> arg1)
	    throws Db4oIOException, DatabaseClosedException {
		return delegate.query(arg0, arg1);
	}

	public <TargetType> ObjectSet<TargetType> query(Predicate<TargetType> arg0) throws Db4oIOException,
	    DatabaseClosedException {
		return delegate.query(arg0);
	}

	public <T> ObjectSet<T> queryByExample(Object arg0) throws Db4oIOException, DatabaseClosedException {
		return delegate.queryByExample(arg0);
	}

	public void rollback() throws Db4oIOException, DatabaseClosedException, DatabaseReadOnlyException {
		delegate.rollback();
	}

	public void store(IDObject obj) throws DatabaseClosedException, DatabaseReadOnlyException {
		delegate.store(obj);
	}
	
	public void store(Object arg0) throws DatabaseClosedException, DatabaseReadOnlyException {
		if (arg0 instanceof IDObject) {
			RequirsiveActivator.activate((IDObject)arg0, delegate);
		}
		delegate.store(arg0);
	}

	@Override
	public void setRollbackOnly() {
		rollbackOnly = true;
	}

	public boolean isRollbackOnly() {
		return rollbackOnly;
	}

	@Override
	public String toString() {
		return "Session[" + id + "] is" + (ext().isClosed() ? "closed" : "open");
	}

}
