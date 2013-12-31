package org.reluxa.bid;

import org.reluxa.vaadin.widget.AbstractColumnGenerator;


public class PartnerColumnGenerator extends AbstractColumnGenerator<Bid, String> {
	@Override
  public String generateCell(Bid value) {
	  return value.getPartner()!=null?value.getPartner().getFullName():"";
  }
}
