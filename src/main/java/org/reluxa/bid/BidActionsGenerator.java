package org.reluxa.bid;

import javax.inject.Inject;

import org.reluxa.player.service.DeletePlayerEvent;
import org.reluxa.vaadin.util.BeanManagerLookup;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public class BidActionsGenerator implements ColumnGenerator {

	@Inject
	private javax.enterprise.event.Event<DeletePlayerEvent> deleteEvent;
	
	@Override
  public Object generateCell(Table source, final Object itemId, Object columnId) {
		Button deleteButton = new Button("Delete");
		deleteButton.setStyleName("small");
		
		deleteButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				DeleteBidEvent bidDelete = new DeleteBidEvent((Bid)itemId);
				BeanManagerLookup.lookup().fireEvent(bidDelete);
			}
		});
		
		return deleteButton;
  }

}
