package org.reluxa.message;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import org.reluxa.AbstractView;
import org.reluxa.bid.view.CurrentWeekBidView;
import org.reluxa.player.Player;
import org.reluxa.player.service.PlayerServiceIF;
import org.reluxa.vaadin.widget.CustomBeanItemContainer;
import org.reluxa.vaadin.widget.Icon;
import org.reluxa.vaadin.widget.IconButtonFactory;

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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

@CDIView(MessageView.VIEW_NAME)
@RolesAllowed(value = Player.ROLE_USER)
public class MessageView extends AbstractView {

	@Inject
	PlayerServiceIF playerService;
	
	@Inject
	MessageServiceIF messageService;
	
	public static final String VIEW_NAME = "message";
	
	private CustomBeanItemContainer<Player> players = new CustomBeanItemContainer<>(
			Player.class, CurrentWeekBidView.class);
	
	@Override
	public void enter(ViewChangeEvent event) {
		players.replaceAll(playerService.getAllPlayers());
	}
	

	@Override
	protected Component getContent() {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(new MarginInfo(true, true, true, true));
		
		verticalLayout.addComponent(getTitleLine());

		final TwinColSelect twin = new TwinColSelect();
		twin.setRightColumnCaption("Recipients");
		twin.setLeftColumnCaption("Users");
		twin.setContainerDataSource(players);
		twin.setItemCaptionPropertyId("fullName");
		
		verticalLayout.addComponent(twin);
		
		final RichTextArea text = new RichTextArea("Message");
		verticalLayout.addComponent(text);
		
		Button sendButton = IconButtonFactory.get("Send", "mail2");
		sendButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Collection<Player> recipients = (Collection<Player>)twin.getValue();
				String message = text.getValue();
				boolean result = messageService.sendMessageOnBehalfOfCurrentUser(recipients, message);
				if (result) {
					Notification.show("Messages were successfullly sent!", Type.HUMANIZED_MESSAGE);
				} else {
					Notification.show("Could not sent the message!", Type.HUMANIZED_MESSAGE);
				}
			}
		});
		verticalLayout.addComponent(sendButton);
		addSpacer(verticalLayout);
		return verticalLayout;
	}


	private Component getTitleLine() {
		HorizontalLayout titleLine = new HorizontalLayout();
		titleLine.setWidth("100%");
		Label label = new Label("<h1>"+Icon.get("bullhorn")+"Send message</h1>",ContentMode.HTML);
		label.setWidth("100%");
		titleLine.addComponent(label);
		titleLine.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
		return titleLine;
  }


}
