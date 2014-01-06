package org.reluxa.vaadin.util;
import org.apache.commons.lang3.StringUtils;

import com.vaadin.ui.DefaultFieldFactory;


public class StringUtil {
	
	public static String fieldToSentenceCase(String str) {
		return StringUtils.capitalize(DefaultFieldFactory.createCaptionByPropertyId(str).toLowerCase());
	}

}
