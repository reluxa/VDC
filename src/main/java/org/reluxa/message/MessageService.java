package org.reluxa.message;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.reluxa.AbstractService;
import org.reluxa.mail.MailSender;
import org.reluxa.mail.MailSenderIF;
import org.reluxa.player.Player;
import org.reluxa.vaadin.auth.VaadinAccessControl;

public class MessageService extends AbstractService implements MessageServiceIF {
	
	@Inject
	MailSender mailSender;
	
	@Inject
	VaadinAccessControl accessControl;
	
	public boolean sendMessageOnBehalfOfCurrentUser(Collection<Player> recipients, String text) {
		ArrayList<String> recipientEmails = new ArrayList<>();
		for (Player player : recipients) {
	    recipientEmails.add(player.getEmail());
    }
		String subject = "Message from "+ accessControl.getCurrentPlayer().getFullName();
		return mailSender.sendMail(recipientEmails, subject, text, text);
	}
	
}
