package com.ms.ws.model.request;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SwapAssigneeRequest {

  private List<LocalDate> datesToSwap; // List of dates in "yyyy-MM-dd" format

  private List<String> supportTypes; // "primary" or "secondary"

}
