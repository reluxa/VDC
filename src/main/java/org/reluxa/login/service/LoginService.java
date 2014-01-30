package org.reluxa.login.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.reluxa.AbstractService;
import org.reluxa.Log;
import org.reluxa.mail.EmailComposer;
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
  
  @Inject
  EmailComposer composer;

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
      String resetEmail = composer.getPasswordResetEmail(pwReset);
      result = mailSender.sendMail(Arrays.asList(email) , "Password reset", null, resetEmail);
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
  


}
