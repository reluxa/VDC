package org.reluxa.mail;

public interface MailSenderIF {
  
  public static String SENDER_ADDRESS = "BLHSE Squash <noreply@squash.reluxa.org>"; 
  
  public boolean sendMail(String sender, String recipient, String subject, String body, String htmlText);

}
