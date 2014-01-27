package org.reluxa.player.view;

import java.util.Collection;

import org.reluxa.player.Player;
import org.reluxa.player.service.PlayerServiceIF;
import org.reluxa.vaadin.widget.IconButtonFactory;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MemberShipValiditySetWindow extends Window {

	public MemberShipValiditySetWindow(final Collection<Player> selectedPlayers, final PlayerServiceIF ps) {
		this.setModal(true);
		this.setClosable(false);
		this.setResizable(false);
		this.setCaption("Select membership validity date:");
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		final InlineDateField pdf = new InlineDateField();
		
		HorizontalLayout buttonLine = new HorizontalLayout();
		buttonLine.setSpacing(true);
		buttonLine.setSizeFull();
		
		Button save = IconButtonFactory.get("Save", "disk");
		save.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				for (Player player: selectedPlayers) {
					player.setMembershipValidUntil(pdf.getValue());
        }
				ps.updateUser(selectedPlayers);
				MemberShipValiditySetWindow.this.close();
			}
		});
		save.setWidth(null);
		
		Button cancel = IconButtonFactory.get("Cancel", "cancel-circle");
		cancel.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				MemberShipValiditySetWindow.this.close();
			}
		});
		save.setWidth(null);
		
		layout.addComponent(pdf);
		
		buttonLine.addComponent(save);
		buttonLine.addComponent(cancel);
		buttonLine.setComponentAlignment(save, Alignment.MIDDLE_CENTER);
		layout.addComponent(buttonLine);
		setContent(layout);
	}

	
}
