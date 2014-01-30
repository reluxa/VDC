package org.reluxa.mail;

import java.util.Collection;

public interface MailSenderIF {
  
  //public static String SENDER_ADDRESS = "BLHSE Squash <noreply@squash.reluxa.org>"; 
  
  public boolean sendMail(Collection<String> recipients, String subject, String body, String htmlText);

}
