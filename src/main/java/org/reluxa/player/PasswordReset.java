package org.reluxa.player;

import lombok.Data;

import org.reluxa.model.IDObject;

@Data
public class PasswordReset implements IDObject {
  
  public long id;
  
  public String uuid;
  
  public Player player;

}
