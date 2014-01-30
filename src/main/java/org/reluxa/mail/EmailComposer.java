package org.reluxa.mail;

import java.io.StringWriter;

import javax.inject.Inject;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.reluxa.AbstractService;
import org.reluxa.player.PasswordReset;
import org.reluxa.player.view.PasswordResetView;
import org.reluxa.settings.service.SettingsService;

import com.vaadin.server.Page;

public class EmailComposer extends AbstractService {
  
  @Inject 
  SettingsService settings;
  
  public String getPasswordResetEmail(PasswordReset pr) {
    StringWriter sw = new StringWriter();
    VelocityContext context = new VelocityContext();
    context.put("link", getRestLink(pr));
    context.put("user", pr.getPlayer().getFullName());
    Velocity.evaluate(context, sw, "log tag", settings.getConfig().getPasswordResetTemplate());
    return sw.toString();
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
