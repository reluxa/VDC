package org.reluxa.bid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.reluxa.AbstractView;
import org.reluxa.bid.service.BidServiceIF;
import org.reluxa.player.Player;
import org.reluxa.player.service.PlayerServiceIF;
import org.reluxa.time.TimeServiceIF;
import org.reluxa.vaadin.util.TableUtils;
import org.reluxa.vaadin.widget.CustomBeanItemContainer;
import org.reluxa.vaadin.widget.Icon;
import org.reluxa.vaadin.widget.IconButtonFactory;
import org.reluxa.vaadin.widget.TableBeanFactory;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

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
	
	@Inject
	private TimeServiceIF timeService;

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
		bids.replaceAll(bidService.getAllBids(timeService.getWeekBegin()));
	}

	@Override
	protected Component getContent() {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(new MarginInfo(true, true, true, true));
		
		verticalLayout.addComponent(getTitleLine());

		Button createButton = IconButtonFactory.get("Place new bid", "target"); 
		HorizontalLayout newBidSection = new HorizontalLayout();
		newBidSection.setHeight("50px");
		newBidSection.setSpacing(true);
		newBidSection.addComponent(createButton);
		final ComboBox friends = new ComboBox(null, players);
		friends.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		friends.setItemCaptionPropertyId("fullName");
		friends.setVisible(false);
		friends.setNullSelectionAllowed(false);
		// friends.setValue(friends.getItemIds().iterator().next());

		final ComboBox type = new ComboBox(null, Arrays.asList("Bid alone",
				"Bid with a friend"));
		type.setTextInputAllowed(false);
		type.setImmediate(true);
		type.setNullSelectionAllowed(false);
		type.setValue(type.getItemIds().iterator().next());

		type.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				friends.setVisible((event.getProperty().getValue()
						.equals("Bid with a friend")));
			}
		});

		createButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Bid bid = new Bid();
				bid.setCreationTime(new Date());
				bid.setCreator(accessControl.getCurrentPlayer());

				String value = (String) type.getValue();
				if ("Bid alone".equals(value)) {
					bid.setType(BidType.SINGLE.toString());
					bid.setStatus(BidStatus.PENDING.toString());
				} else {
					Player friend = (Player)friends.getValue();
					BeanItem<Player> item = (BeanItem<Player>)friends.getItem(friend);
					
					if (item == null || item.getBean() == null) {
						Notification.show("A friend must be selected!", Type.ERROR_MESSAGE);
						return;
					}
					
					Player friend2 = item.getBean();
			
					//System.out.println("one:"+System.identityHashCode(friend)+"\tother:"+System.identityHashCode(friend2));
			
					bid.setPartner(friend2);
					bid.setType(BidType.WITH_FRIEND.toString());
					bid.setStatus(BidStatus.WAITING_FOR_APPOVAL.toString());
				}
				bidService.createBid(bid);
			}
		});

		newBidSection.addComponent(type);
		newBidSection.addComponent(friends);
		
		verticalLayout.addComponent(newBidSection);

		bidsTable = bids.createTable();
		bidsTable.setImmediate(true);
		bidsTable.setSizeFull();
		bidsTable.setPageLength(10);
		verticalLayout.addComponent(bidsTable);
		return verticalLayout;
	}

	private HorizontalLayout getTitleLine() {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy.MM.dd");
		
		LocalDate begin = new LocalDate(timeService.getWeekBegin());
		LocalDate end = new LocalDate(timeService.getWeekEnd());

		StringBuffer buf = new StringBuffer();
		buf.append("CW");
		buf.append(timeService.getCurrentWeekNumber());
		buf.append(": ");
		buf.append(begin.toString(format));
		buf.append(" - ");
		buf.append(end.toString(format));

		HorizontalLayout titleLine = new HorizontalLayout();
		titleLine.setSizeFull();
		Label label = new Label("<h1>"+Icon.get("calendar")+"Current week bids</h1>",ContentMode.HTML);
		label.setWidth("100%");
		titleLine.addComponent(label);
		//titleLine.setExpandRatio(label, 1f);
		titleLine.setComponentAlignment(label, Alignment.MIDDLE_LEFT);


		Label weekLabel = new Label("<h2>"+buf.toString()+"</h2>",ContentMode.HTML);
		weekLabel.setWidth(null);
		titleLine.addComponent(weekLabel);
		titleLine.setComponentAlignment(weekLabel, Alignment.MIDDLE_RIGHT);
		return titleLine;
	}

	public void updateModel(@Observes BidModelChanged event) {
		if (event.getDeleted() != null) {
			for (Bid bid : event.getDeleted()) {
				bids.removeItem(bid.getId());
				Notification.show("Bid was removed",Type.TRAY_NOTIFICATION);
			}
		}
		if (event.getCreated() != null) {
			bids.addItem(event.getCreated().getId(), event.getCreated());
			Notification.show("Bid was added",Type.TRAY_NOTIFICATION);
		}

		if (event.getUpdated() != null) {
			for (Bid bid : event.getUpdated()) {
				TableUtils.updateRow(bid.getId(), bids);
				Notification.show("Bid was updated",Type.TRAY_NOTIFICATION);
			}
		}
	}

}
