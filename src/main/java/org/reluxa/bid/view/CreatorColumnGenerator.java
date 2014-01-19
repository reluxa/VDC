package org.reluxa.bid.view;

import org.reluxa.bid.Bid;
import org.reluxa.vaadin.widget.AbstractColumnGenerator;


public class CreatorColumnGenerator extends AbstractColumnGenerator<Bid, String> {
	
	@Override
  public String generateCell(Bid value) {
	  return value.getCreator().getFullName();
  }
}
