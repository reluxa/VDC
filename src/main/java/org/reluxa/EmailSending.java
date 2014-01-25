package org.reluxa;

import java.util.Arrays;

import org.reluxa.mail.MailSender;

public class EmailSending {

  public static void main(String[] args) {
    MailSender ms = new MailSender();
    ms.sendMail("noreply@squash.reluxa.org", Arrays.asList("reluxa@gmail.com"), "test", "hello", "hello");
  }
}
