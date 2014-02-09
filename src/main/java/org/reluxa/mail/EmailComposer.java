package org.reluxa.mail;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.Setter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.reluxa.AbstractService;
import org.reluxa.bid.Bid;
import org.reluxa.bid.BidStatus;
import org.reluxa.player.PasswordReset;
import org.reluxa.player.Player;
import org.reluxa.player.view.PasswordResetView;
import org.reluxa.settings.Config;
import org.reluxa.settings.CurrentConfig;

import com.vaadin.server.Page;

public class EmailComposer extends AbstractService {
  
  @Inject @CurrentConfig @Setter
  private Config config;
  
  public String getPasswordResetEmail(PasswordReset pr) {
    StringWriter sw = new StringWriter();
    VelocityContext context = new VelocityContext();
    context.put("link", getRestLink(pr));
    context.put("user", pr.getPlayer().getFullName());
    Velocity.evaluate(context, sw, "log tag", config.getPasswordResetTemplate());
    return sw.toString();
  }
  
  public String getWeeklyEvalEmail(List<Bid> bids, Player player) {
    StringWriter sw = new StringWriter();
    VelocityContext context = new VelocityContext();
    
    context.put("user", player.getFullName());
    context.put("number", config.getNumberOfEventsPerWeek());
    context.put("bids", bids);
    context.put("win", wonBids(bids, player));
    
    Velocity.evaluate(context, sw, "log tag", config.getWeeklyEvaluationTemplate());
    return sw.toString();
  }
  
  private List<Bid> wonBids(List<Bid> bids, Player player) {
  	List<Bid> result = new ArrayList<>();
  	for (Bid bid : bids) {
  		if (BidStatus.WON.toString().equals(bid.getStatus()) && 
  				(player.equals(bid.getCreator()) || player.equals(bid.getPartner()))) {
  			result.add(bid);
  		}
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
