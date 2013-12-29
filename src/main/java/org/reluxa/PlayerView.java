package org.reluxa;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.reluxa.model.Player;
import org.reluxa.playerservice.DeletePlayerEvent;
import org.reluxa.playerservice.PlayerService;
import org.reluxa.vaadin.widget.CustomBeanItemContainer;
import org.reluxa.vaadin.widget.GeneratedForm;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@CDIView(PlayerView.VIEW_NAME)
@RolesAllowed(value = Player.ROLE_USER)
//@RequestScoped
public class PlayerView extends AbstractView {
	
	public static final String VIEW_NAME = "player_view";

	@Inject
	private javax.enterprise.event.Event<DeletePlayerEvent> deleteEvent;

	@Inject
	private PlayerService playerService;

	private CustomBeanItemContainer<Player> container = new CustomBeanItemContainer<>(Player.class, PlayerView.class);

	private GeneratedForm<Player> detailForm = new GeneratedForm<>(Player.class, PlayerView.class);
	
	private VerticalLayout detailHolder = new VerticalLayout();

	public Component getContent() {
		final Table table = new Table(null, container);
		table.setSizeFull();
		table.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				showDetailPanel(EditMode.UPDATE, (Player) table.getValue());
			}
		});
		VerticalLayout tablePanel = new VerticalLayout();
		// tablePanel.setSizeFull();
		HorizontalLayout buttonPanel = new HorizontalLayout();
		Button deletePlayerButton = new Button("Delete");
		deletePlayerButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				deleteEvent.fire(new DeletePlayerEvent((Player) table.getValue()));
				table.removeItem(table.getValue());
			}
		});

		Button createPlayer = new Button("Create");
		createPlayer.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				showDetailPanel(EditMode.CREATE, new Player());
			}
		});
		buttonPanel.addComponent(deletePlayerButton);
		buttonPanel.addComponent(createPlayer);

		tablePanel.setSpacing(true);
		tablePanel.addComponent(new Label("<h1>Player Administration</h1>", ContentMode.HTML));
		tablePanel.addComponent(table);
		tablePanel.addComponent(buttonPanel);
		tablePanel.addComponent(detailHolder);
		
		tablePanel.setMargin(new MarginInfo(true, true, true, true));
		table.setImmediate(true);
		table.setSelectable(true);
		return tablePanel;
	}
	
	public void showDetailPanel(final EditMode editMode, final Player bean) {
		detailHolder.removeAllComponents();
		if (bean == null) {
			return;
		}
		HorizontalLayout detailButtons = new HorizontalLayout();
		detailForm.setBean(bean);
		Button saveButton = new Button("Save", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (EditMode.CREATE.equals(editMode)) {
					try {
		        playerService.createUser(bean);
		        Notification.show("User was created succesfully.", Notification.Type.TRAY_NOTIFICATION);
	        } catch (DuplicateUserException e) {
	    			Notification.show("User already exists with the same name!", Notification.Type.ERROR_MESSAGE);
	        }
				} else if (EditMode.UPDATE.equals(editMode)) {
						playerService.updateUser(bean);
						Notification.show("User was updated succesfully.", Notification.Type.TRAY_NOTIFICATION);
				}
        container.removeAllItems();
        container.addAll(playerService.getAllPlayers());
			}
		});
		detailButtons.addComponent(saveButton);
		
		detailHolder.addComponent(new Label("<h2>"+getDetailHeader(editMode)+"</h2>", ContentMode.HTML));
		detailHolder.addComponent(detailForm);
		detailHolder.addComponent(detailButtons);
	}
	
	private String getDetailHeader(EditMode editMode) {
		if (EditMode.CREATE.equals(editMode)) {
			return "Create new player";
		} else if (EditMode.UPDATE.equals(editMode)) {
			return "Update player";
		}
		return "Details";
	}


	@Override
	public void enter(ViewChangeEvent event) {
		container.addAll(playerService.getAllPlayers());
	}

}
