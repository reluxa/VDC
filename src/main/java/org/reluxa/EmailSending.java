package org.reluxa;

import org.reluxa.mail.MailSender;

public class EmailSending {

  public static void main(String[] args) {
    MailSender ms = new MailSender();
    ms.sendMail("noreply@squash.reluxa.org", "reluxa@gmail.com", "test", "hello", "hello");
  }
}
