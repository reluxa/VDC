package org.reluxa.login.service;

import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.reluxa.AbstractService;
import org.reluxa.Log;
import org.reluxa.mail.MailSenderIF;
import org.reluxa.player.PasswordReset;
import org.reluxa.player.Player;
import org.reluxa.player.view.PasswordResetView;

import com.db4o.ObjectSet;
import com.vaadin.server.Page;

@Log
public class LoginService extends AbstractService {
  
  @Inject
  MailSenderIF mailSender;

  public boolean isUserExists(Player player) {
    ObjectSet<Player> users = db.queryByExample(player);
    return users.size() > 0;
  }

  public Player getUser(String email) {
    Player player = new Player();
    player.setEmail(email);
    ObjectSet<Player> users = db.queryByExample(player);
    return users.get(0);
  }
  
  public boolean passwordResetRequest(String email) {
    boolean result = false;
    Player player = new Player();
    player.setEmail(email);
    ObjectSet<Player> users = db.queryByExample(player);
    if (users.size() > 0) {
      PasswordReset pwReset = new PasswordReset();
      pwReset.setUuid(UUID.randomUUID().toString());
      pwReset.setPlayer(users.get(0));
      db.store(pwReset);
      StringBuffer buffer = new StringBuffer();
      buffer.append("Dear "+users.get(0).getFullName());
      buffer.append("\r\n\r\n");
      buffer.append("Your password reset link is: ");
      buffer.append(getRestLink(pwReset));
      buffer.append("\r\n\r\n");
      buffer.append("Best Regards, BLHSE Squash");
      result = mailSender.sendMail(MailSenderIF.SENDER_ADDRESS, email, "Password reset", buffer.toString(), null);
    }
    return result;
  }
  
  public boolean resetPassword(String uuid, String password) {
    boolean result = false;
    if (StringUtils.isEmpty(uuid)) {
      throw new IllegalArgumentException("UUID must be set!");
    }

    PasswordReset pwReset = new PasswordReset();
    pwReset.setUuid(uuid);
    ObjectSet<PasswordReset> objects = db.queryByExample(pwReset);
    
    if (objects.size() == 1) {
      PasswordReset actual = objects.get(0);
      Player dbPlayer = actual.getPlayer();
      dbPlayer.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
      db.store(dbPlayer);
      db.delete(actual);
      result = true;
    }
    return result;
  }
  
  private String getRestLink(PasswordReset pwReset) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(Page.getCurrent().getLocation().getScheme());
    buffer.append("://");
    buffer.append(Page.getCurrent().getLocation().getHost());
    buffer.append(":");
    buffer.append(Page.getCurrent().getLocation().getPort());
    buffer.append(Page.getCurrent().getLocation().getPath());
    buffer.append("?");
    buffer.append("id=");
    buffer.append(pwReset.getUuid());
    buffer.append("#!");
    buffer.append(PasswordResetView.VIEW_NAME);
    return buffer.toString();
  }

}
