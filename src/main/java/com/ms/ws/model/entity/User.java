package com.ms.ws.model.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@Getter
@Setter
public class User {

  @Id
  private String id;
  private String name;
  private String email;
  private String passwordHash;
  private Date createdOn;
  private Date lastLogin;
  private boolean isActive;
  private String team;

}
