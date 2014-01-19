package org.reluxa.bid.view;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import org.reluxa.AbstractView;
import org.reluxa.bid.Bid;
import org.reluxa.bid.service.BidService;
import org.reluxa.player.Player;
import org.reluxa.vaadin.widget.Icon;
import org.reluxa.vaadin.widget.TableBeanFactory;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@CDIView(TicketView.VIEW_NAME)
@RolesAllowed(value = Player.ROLE_USER)
public class TicketView extends AbstractView {

	public static final String VIEW_NAME = "ticketView";
	
	@Inject
	private BidService bidService;
	
	private TableBeanFactory<Long, Bid> bids = new TableBeanFactory<>(Bid.class, TicketView.class);
	
	@Override
	public void enter(ViewChangeEvent event) {
		bids.replaceAll(bidService.getTicketsForPlayer(accessControl.getCurrentPlayer()));
		bids.sort(new Object[]{"creationTime"}, new boolean[]{false});
	}

	private HorizontalLayout getTitleLine() {
		HorizontalLayout titleLine = new HorizontalLayout();
		titleLine.setSizeFull();
		Label label = new Label("<h1>" + Icon.get("ticket") + "My tickets</h1>", ContentMode.HTML);
		label.setWidth("100%");
		titleLine.addComponent(label);
		titleLine.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
		return titleLine;
	}

	@Override
	protected Component getContent() {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(new MarginInfo(true, true, true, true));
		verticalLayout.addComponent(getTitleLine());
		Table bidsTable = bids.createTable();
		bidsTable.setImmediate(true);
		bidsTable.setSizeFull();
		bidsTable.setPageLength(10);
		verticalLayout.addComponent(bidsTable);
		return verticalLayout;
	}

}
