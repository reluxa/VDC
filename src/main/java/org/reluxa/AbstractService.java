package org.reluxa;

import javax.inject.Inject;

import org.reluxa.db.Session;

public class AbstractService {

	@Inject
	protected Session db;

}
