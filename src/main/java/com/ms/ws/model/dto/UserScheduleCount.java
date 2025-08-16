package com.ms.ws.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserScheduleCount {
  private String id;
  private long primaryCount;
  private long secondaryCount;
}
