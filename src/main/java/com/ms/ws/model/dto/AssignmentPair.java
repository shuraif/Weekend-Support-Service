package com.ms.ws.model.dto;

import com.ms.ws.model.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentPair {
  private User primary;
  private User secondary;
}

