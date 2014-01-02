package org.reluxa.bid;

import java.beans.Beans;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.reluxa.AbstractView;
import org.reluxa.bid.service.BidService;
import org.reluxa.player.Player;
import org.reluxa.player.service.PlayerService;
import org.reluxa.vaadin.widget.CustomBeanItemContainer;
import org.reluxa.vaadin.widget.TableFactory;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.AbstractBeanContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

@CDIView(CurrentWeekBidView.VIEW_NAME)
@RolesAllowed(value = Player.ROLE_USER)
public class CurrentWeekBidView extends AbstractView {
	
	public static final String VIEW_NAME = "currentWeekBids";
	
	private CustomBeanItemContainer<Player> players = new CustomBeanItemContainer<>(Player.class, CurrentWeekBidView.class);
	
	private TableFactory<Bid> bids = new TableFactory<>(Bid.class, CurrentWeekBidView.class);

	@Inject
	private PlayerService playerService;
	
	@Inject
	private BidService bidService;
	
	private Table bidsTable;
	
	@Override
  public void enter(ViewChangeEvent event) {
		System.out.println("Entering view:"+this);
		players.replaceAll(playerService.getAllPlayers());
		players.sort(new Object[] {"fullName"} , new boolean[] {true});
		bids.replaceAll(bidService.getAll());
  }

	@Override
  protected Component getContent() {
		GridLayout gridlayout = new GridLayout();
		gridlayout.setSizeFull();
		gridlayout.setSpacing(true);
		gridlayout.setMargin(new MarginInfo(true, true, true, true));
		
		
		gridlayout.addComponent(new Label("<h2>Current week bids</h2>",ContentMode.HTML));
		Button createButton = new Button("create new bid"); 
		gridlayout.addComponent(createButton);

		final ComboBox friends = new ComboBox("Friends",players);
		friends.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		friends.setItemCaptionPropertyId("fullName");
		friends.setVisible(false);
		friends.setNullSelectionAllowed(false);
		//friends.setValue(friends.getItemIds().iterator().next());
		
		final ComboBox box = new ComboBox("Type", Arrays.asList("Bid alone","Bid with a fried"));
		box.setTextInputAllowed(false);
		box.setImmediate(true);
		box.setNullSelectionAllowed(false);
		box.setValue(box.getItemIds().iterator().next());
		
		box.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				friends.setVisible((event.getProperty().getValue().equals("Bid with a fried")));
			}
		});
		
		createButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
//				for (int i=0;i<1000;i++) {
				Bid bid = new Bid();
				bid.setUid(UUID.randomUUID());
				bid.setCreationTime(new Date());
				bid.setCreator(accessControl.getCurrentPlayer());

				String value = (String) box.getValue();
				if ("Bid alone".equals(value)) {
					bid.setType(BidType.SINGLE.toString());
					bid.setStatus(BidStatus.PENDING.toString());
				} else {
					bid.setPartner((Player) friends.getValue());
					bid.setType(BidType.WITH_FRIEND.toString());
					bid.setStatus(BidStatus.WAITING_FOR_APPOVAL.toString());
				}
				bidService.createBid(bid);
//				}
			}
		});
		
		gridlayout.addComponent(box);
		gridlayout.addComponent(friends);
		
		bidsTable = bids.createTable();
		bidsTable.setImmediate(true);
		bidsTable.setSizeFull();
		
		

		gridlayout.addComponent(bidsTable);
		
		return gridlayout;
  }
	
	public void updateModel(@Observes BidModelChanged event) {
		if (event.getDeleted() != null) {
			for(Bid bid : event.getDeleted()) {
				System.out.println("Before:"+bids.size());
				bids.removeItem(bid);
				System.out.println("After:"+bids.size());
				bidsTable.removeItem(bid);
			}
		}
		if (event.getCreated() != null) {
			bids.addItem(event.getCreated());
			bidsTable.addItem(event.getCreated());
		}
		
		if (event.getUpdated() != null) {
			
				
//			Bid bid = event.getUpdated()[0];
//			System.out.println("udpdated model called...");
//			System.out.println(bid);
////			bids.refresh();
////			Collection<?> pids = bidsTable.getContainerPropertyIds();
////			for (Object pid : pids) {
////				System.out.println(event.getUpdated()[0]);
////				
////				Property prop = bidsTable.getContainerProperty(event.getUpdated()[0], pid);
////				((MethodProperty)prop).fireValueChange();
////			}
//			bids.getItem(bid);
//			bids.removeItem(bid);
//			bids.addItem(bid);
//
//			List<Bid> bs = bids.getItemIds();
//			System.out.println(bs);
//			bids.refresh();
				//bids.getItem(event.getUpdated()[0]).
			
			//bidsTable.refreshRowCache();
		}
		//bidsTable.setContainerDataSource(bids);

	}

}
