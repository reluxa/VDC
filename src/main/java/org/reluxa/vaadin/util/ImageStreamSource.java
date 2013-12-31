package org.reluxa.vaadin.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vaadin.server.StreamResource.StreamSource;

public class ImageStreamSource implements StreamSource {

	private final byte[] data;
	
	public ImageStreamSource(byte[] source) {
		data = source;
	}
	
	@Override
  public InputStream getStream() {
		ByteArrayInputStream bios = new ByteArrayInputStream(data);
		return bios;
  }

}
