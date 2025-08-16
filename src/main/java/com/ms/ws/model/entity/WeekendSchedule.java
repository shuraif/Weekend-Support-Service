package com.ms.ws.model.entity;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("WeekendSchedule")
@Getter @Setter
public class WeekendSchedule {

  @Id
  private String id;

  private LocalDate date;

  //@DBRef
  private User primary;

  //@DBRef
  private User secondary;

}
