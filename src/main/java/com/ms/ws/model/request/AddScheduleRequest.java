package com.ms.ws.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddScheduleRequest {

  private String date;
  private String primaryId;
  private String secondaryId;

}
