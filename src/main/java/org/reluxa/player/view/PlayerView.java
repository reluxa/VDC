package org.reluxa.player.view;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.reluxa.AbstractView;
import org.reluxa.player.Player;
import org.reluxa.player.event.DeletePlayerEvent;
import org.reluxa.player.event.PlayerModelChanged;
import org.reluxa.player.service.DuplicateUserException;
import org.reluxa.player.service.PlayerServiceIF;
import org.reluxa.vaadin.util.ImageStreamSource;
import org.reluxa.vaadin.widget.GeneratedForm;
import org.reluxa.vaadin.widget.Icon;
import org.reluxa.vaadin.widget.IconButtonFactory;
import org.reluxa.vaadin.widget.TableBeanItemFactory;
import org.vaadin.easyuploads.MultiFileUpload;

import com.google.gwt.thirdparty.guava.common.io.Files;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@CDIView(PlayerView.VIEW_NAME)
@RolesAllowed(value = Player.ROLE_ADMIN)
public class PlayerView extends AbstractView {
	
	public static final String VIEW_NAME = "players";

	@Inject
	private javax.enterprise.event.Event<DeletePlayerEvent> deleteEvent;

	@Inject
	private PlayerServiceIF playerService;

	private TableBeanItemFactory<Player> container = new TableBeanItemFactory<>(Player.class, PlayerView.class);

	private GeneratedForm<Player> detailForm = new GeneratedForm<>(Player.class, PlayerView.class);
	
	private VerticalLayout detailHolder = new VerticalLayout();

	public Component getContent() {
		final Table table = container.createTable();
		table.setSizeFull();
		table.setMultiSelect(true);
		table.setPageLength(9);
		table.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (((Collection)table.getValue()).size() == 1) {
					showDetailPanel(EditMode.UPDATE, ((Collection<Player>)table.getValue()).iterator().next());	
				} else if ((((Collection)table.getValue()).size() == 1)) {
					showDetailPanel(EditMode.UPDATE, null);
				}
			}
		});
		final VerticalLayout tablePanel = new VerticalLayout();
		HorizontalLayout buttonPanel = new HorizontalLayout();
		Button deletePlayerButton = IconButtonFactory.get("Delete", "remove2"); 
		deletePlayerButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				deleteEvent.fire(new DeletePlayerEvent((Player) table.getValue()));
				table.removeItem(table.getValue());
			}
		});

		Button createPlayer = IconButtonFactory.get("Create", "user-add");
		createPlayer.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				showDetailPanel(EditMode.CREATE, new Player());
			}
		});
		
		Button setMembershipTime = IconButtonFactory.get("Set membership validity", "alarm2");
		setMembershipTime.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Collection<Player> players = (Collection<Player>)table.getValue();
				MemberShipValiditySetWindow sw = new MemberShipValiditySetWindow(players, playerService);
				Window subwindow = new Window("Select membership time");
				sw.center();
				getUI().addWindow(sw);
			}
		});
		

		
		buttonPanel.addComponent(deletePlayerButton);
		buttonPanel.addComponent(createPlayer);
		buttonPanel.addComponent(setMembershipTime);
		buttonPanel.setSpacing(true);

		tablePanel.setSpacing(true);
		tablePanel.addComponent(new Label("<h1>"+Icon.USERS+"Player administration</h1>", ContentMode.HTML));
		tablePanel.addComponent(table);
		tablePanel.addComponent(buttonPanel);
		tablePanel.addComponent(detailHolder);
		
		tablePanel.setMargin(new MarginInfo(true, true, true, true));
		table.setImmediate(true);
		table.setSelectable(true);
		return tablePanel;
	}
	
	public void showDetailPanel(final EditMode editMode, final Player player) {
		detailHolder.removeAllComponents();
		if (player == null) {
			return;
		}
		
		HorizontalLayout columns = new HorizontalLayout();
		columns.setSpacing(true);
		
		HorizontalLayout detailButtons = new HorizontalLayout();
		detailButtons.setSpacing(true);
		detailForm.setBean(player);
		
		Button clearImage = IconButtonFactory.get("clear image", "email");
		clearImage.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				player.setImage(null);
			}
		});
		
		detailButtons.addComponent(clearImage);

		
		Button saveButton = IconButtonFactory.get("save", "disk");
		saveButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (EditMode.CREATE.equals(editMode)) {
					try {
		        playerService.createUser(player);
		        Notification.show("User was created succesfully.", Notification.Type.TRAY_NOTIFICATION);
	        } catch (DuplicateUserException e) {
	    			Notification.show("User already exists with the same name!", Notification.Type.ERROR_MESSAGE);
	        }
				} else if (EditMode.UPDATE.equals(editMode)) {
						playerService.updateUser(player);
						Notification.show("User was updated succesfully.", Notification.Type.TRAY_NOTIFICATION);
				}
        container.removeAllItems();
        container.addAll(playerService.getAllPlayers());
			}
		});
		detailButtons.addComponent(saveButton);
		
		detailHolder.addComponent(new Label("<h2>"+getDetailHeader(editMode)+"</h2>", ContentMode.HTML));
		detailHolder.addComponent(columns);
		detailHolder.addComponent(detailButtons);
		
		columns.addComponent(getProfileImage(player));
		columns.addComponent(detailForm);

	}
	
	
	public Component getProfileImage(final Player bean) {
		
		VerticalLayout layout = new VerticalLayout();

		final Image img = new Image(null,null);
		img.setHeight("100px");

		if (bean != null && bean.getImage() != null && bean.getImage().length > 0) {
			img.setSource(new StreamResource(new ImageStreamSource(bean.getImage()), System.currentTimeMillis()+"profile.jpg"));
		} else {
			img.setSource(new ThemeResource("img/player.jpg"));
		}
		
		MultiFileUpload upload = new MultiFileUpload() {
			
	    private DragAndDropWrapper dropZone;
			
			@Override
			protected void handleFile(File file, String fileName, String mimeType, long length) {
				try {
	        bean.setImage(Files.toByteArray(file));
	        img.setSource(new StreamResource(new ImageStreamSource(Files.toByteArray(file)), System.currentTimeMillis()+"profile.jpg"));
	        //img.setImmediate(true);
	        //img.markAsDirtyRecursive();
	        //System.out.println("File has been uploaded2");
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
			}
			
			@Override
			public void attach() {
				super.attach();
				Iterator<Component> iter = iterator();
				while (iter.hasNext()) {
					Component comp = iter.next();
					if (comp instanceof DragAndDropWrapper) {
						removeComponent(comp);
						break;
					}
				}
        if (supportsFileDrops()) {
            customPrepareDropZone();
        }
      }

			private void customPrepareDropZone() {
        if (dropZone == null) {
          dropZone = new DragAndDropWrapper(img);
          //dropZone.setStyleName("v-multifileupload-dropzone");
          dropZone.setSizeUndefined();
          addComponent(dropZone, 1);
          dropZone.setDropHandler(this);
          addStyleName("no-horizontal-drag-hints");
          addStyleName("no-vertical-drag-hints");
        }
      }
		};
		upload.setUploadButtonCaption("Change image...");

		layout.addComponent(img);
		layout.addComponent(upload);
		return layout;
	}
	
	private String getDetailHeader(EditMode editMode) {
		if (EditMode.CREATE.equals(editMode)) {
			return "Create new player";
		} else if (EditMode.UPDATE.equals(editMode)) {
			return "Update player";
		}
		return "Details";
	}

	
	public void updateModel(@Observes PlayerModelChanged event) {
		log.debug("event captured");
		container.replaceAll(playerService.getAllPlayers());
	}
	

	@Override
	public void enter(ViewChangeEvent event) {
		container.replaceAll(playerService.getAllPlayers());
	}

}
