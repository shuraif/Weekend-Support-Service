package com.ms.ws.service;

import com.ms.ws.model.request.AddScheduleRequest;
import com.ms.ws.model.request.SwapAssigneeRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface WeekendSupportService {


  ResponseEntity<Boolean> clearAllDocuments( String userId, String collectionName);

  ResponseEntity<?>  getSchedule();

  ResponseEntity<?> fetchSchedule();

  ResponseEntity<?> swapAssignee(String userId, SwapAssigneeRequest request);

  ResponseEntity<?> addAssignee(String userId, AddScheduleRequest addScheduleRequest);

  ResponseEntity<?> deleteSchedule(String userId, List<String> scheduleDates);

  ResponseEntity<?> getActivityLogs(String userId, String date);

  ResponseEntity<?> getAssigneeReport();
}
