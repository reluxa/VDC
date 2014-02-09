package org.reluxa.bid.view;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.reluxa.AbstractView;
import org.reluxa.bid.Bid;
import org.reluxa.bid.service.BidService;
import org.reluxa.player.Player;
import org.reluxa.time.TimeServiceIF;
import org.reluxa.vaadin.widget.Icon;
import org.reluxa.vaadin.widget.IconButtonFactory;
import org.reluxa.vaadin.widget.TableBeanFactory;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@CDIView(BidHistoryView.VIEW_NAME)
@RolesAllowed(value = Player.ROLE_USER)
public class BidHistoryView extends AbstractView {

	public static final String VIEW_NAME = "history";
	
	@Inject
	private TimeServiceIF timeService;
	
	@Inject
	private BidService bidService;
	
	private TableBeanFactory<Long, Bid> bids = new TableBeanFactory<>(Bid.class, BidHistoryView.class);
	
	private Table bidsTable;
	
	private Label weekLabel; 
	
	private LocalDate begin;
	
	@Override
	public void enter(ViewChangeEvent event) {
		begin = new LocalDate(timeService.getWeekBegin()); 
		updateView();
	}

	private void updateView() {
		Collection<Bid> loadedBids = bidService.getAllBids(begin.toDate(), begin.plusDays(7).toDate());
		bids.replaceAll(loadedBids);
		updateWeekLabel();
  }
	
	private void updateWeekLabel() {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy.MM.dd");
		StringBuffer buf = new StringBuffer();
		buf.append("CW");
		buf.append(begin.getWeekOfWeekyear());
		buf.append(": ");
		buf.append(begin.toString(format));
		buf.append(" - ");
		buf.append(begin.plusDays(6).toString(format));
		weekLabel.setValue("<h2>" + buf.toString() + "</h2>");
	}

	private HorizontalLayout getTitleLine() {
		HorizontalLayout titleLine = new HorizontalLayout();
		titleLine.setWidth("100%");
		Label label = new Label("<h1>" + Icon.get("history") + "Bid history</h1>", ContentMode.HTML);
		label.setWidth("100%");
		titleLine.addComponent(label);
		titleLine.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
		weekLabel = new Label("null", ContentMode.HTML);
		weekLabel.setWidth(null);
		//weekLabel.setContentMode(ContentMode.HTML);
		titleLine.addComponent(weekLabel);
		titleLine.setComponentAlignment(weekLabel, Alignment.MIDDLE_RIGHT);
		return titleLine;
	}

	@Override
	protected Component getContent() {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(true);

		verticalLayout.addComponent(getTitleLine());

		Button nextWeek = IconButtonFactory.get("Next week", "arrow-right2");
		nextWeek.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				begin = begin.plusDays(7);
				updateView();
			}
		});
		Button previousWeek = IconButtonFactory.get("Prev week", "arrow-left2");
		previousWeek.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				begin = begin.minusDays(7);
				updateView();
			}
		});
		HorizontalLayout buttonLine = new HorizontalLayout();
		buttonLine.setHeight("50px");
		buttonLine.setSpacing(true);
		buttonLine.addComponent(previousWeek);
		buttonLine.addComponent(nextWeek);

		verticalLayout.addComponent(buttonLine);
		bidsTable = bids.createTable();
		bidsTable.setImmediate(true);
		bidsTable.setWidth("100%");
		bidsTable.setPageLength(10);
		verticalLayout.addComponent(bidsTable);
		addSpacer(verticalLayout);
		return verticalLayout;
	}

}
