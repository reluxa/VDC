package org.reluxa.db;

import java.util.ArrayList;
import java.util.List;

import org.reluxa.AbstractService;

import com.db4o.ObjectSet;
import com.thoughtworks.xstream.XStream;

public class ExportService extends AbstractService {

	public String getExportXML() {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		List<Object> result = new ArrayList<>();
		ObjectSet<Object> content = db.query(Object.class);
		for (Object object : content) {
			result.add(object);
		}
		return xstream.toXML(result);
	}
	
}
