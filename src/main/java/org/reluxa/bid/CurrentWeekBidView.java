package org.reluxa.bid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.reluxa.AbstractView;
import org.reluxa.bid.service.BidServiceIF;
import org.reluxa.player.Player;
import org.reluxa.player.service.PlayerServiceIF;
import org.reluxa.vaadin.util.TableUtils;
import org.reluxa.vaadin.widget.CustomBeanItemContainer;
import org.reluxa.vaadin.widget.TableBeanFactory;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
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

	private CustomBeanItemContainer<Player> players = new CustomBeanItemContainer<>(
			Player.class, CurrentWeekBidView.class);

	private TableBeanFactory<Long, Bid> bids = new TableBeanFactory<>(Bid.class, CurrentWeekBidView.class);

	@Inject
	private PlayerServiceIF playerService;
	
	@Inject
	private BidServiceIF bidService;

	private Table bidsTable;
	
	private Collection<Player> getAllPartners() {
		ArrayList<Player> result = new ArrayList<>(playerService.getAllPlayers());
		Player current = accessControl.getCurrentPlayer();
		for (Player player : result) {
			System.out.println(player.getId()+"\t"+player.getEmail()+"\t "+System.identityHashCode(player));
		}
		result.remove(current);
		return result;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("Entering view:" + this);
		players.replaceAll(getAllPartners());
		players.sort(new Object[] { "fullName" }, new boolean[] { true });
		bids.replaceAll(bidService.getAll());
	}

	@Override
	protected Component getContent() {
		GridLayout gridlayout = new GridLayout();
		gridlayout.setSizeFull();
		gridlayout.setSpacing(true);
		gridlayout.setMargin(new MarginInfo(true, true, true, true));

		gridlayout.addComponent(new Label("<h2>Current week bids</h2>",
				ContentMode.HTML));
		Button createButton = new Button("create new bid");
		gridlayout.addComponent(createButton);

		final ComboBox friends = new ComboBox("Friends", players);
		friends.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		friends.setItemCaptionPropertyId("fullName");
		friends.setVisible(false);
		friends.setNullSelectionAllowed(false);
		// friends.setValue(friends.getItemIds().iterator().next());

		final ComboBox box = new ComboBox("Type", Arrays.asList("Bid alone",
				"Bid with a fried"));
		box.setTextInputAllowed(false);
		box.setImmediate(true);
		box.setNullSelectionAllowed(false);
		box.setValue(box.getItemIds().iterator().next());

		box.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				friends.setVisible((event.getProperty().getValue()
						.equals("Bid with a fried")));
			}
		});

		createButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Bid bid = new Bid();
				bid.setCreationTime(new Date());
				bid.setCreator(accessControl.getCurrentPlayer());

				String value = (String) box.getValue();
				if ("Bid alone".equals(value)) {
					bid.setType(BidType.SINGLE.toString());
					bid.setStatus(BidStatus.PENDING.toString());
				} else {
					Player friend = (Player)friends.getValue();
					BeanItem<Player> item = (BeanItem<Player>)friends.getItem(friend);
					Player friend2 = item.getBean();
			
					System.out.println("one:"+System.identityHashCode(friend)+"\tother:"+System.identityHashCode(friend2));
			
					bid.setPartner(friend2);
					bid.setType(BidType.WITH_FRIEND.toString());
					bid.setStatus(BidStatus.WAITING_FOR_APPOVAL.toString());
				}
				bidService.createBid(bid);
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
			for (Bid bid : event.getDeleted()) {
				bids.removeItem(bid.getId());
			}
		}
		if (event.getCreated() != null) {
			bids.addItem(event.getCreated().getId(), event.getCreated());
		}

		if (event.getUpdated() != null) {
			for (Bid bid : event.getUpdated()) {
				TableUtils.updateRow(bid.getId(), bids);
			}
		}
	}

}
