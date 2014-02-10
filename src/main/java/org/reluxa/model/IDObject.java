package org.reluxa.model;

import java.io.Serializable;

public interface IDObject extends Serializable {

	void setId(long id);
	
	long getId();
	
}
