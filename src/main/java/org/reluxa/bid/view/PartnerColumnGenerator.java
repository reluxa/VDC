package org.reluxa.bid.view;

import org.reluxa.bid.Bid;
import org.reluxa.vaadin.widget.AbstractColumnGenerator;


public class PartnerColumnGenerator extends AbstractColumnGenerator<Bid, String> {
	@Override
  public String generateCell(Bid value) {
	  return value.getPartner()!=null?value.getPartner().getFullName():"";
  }
}
