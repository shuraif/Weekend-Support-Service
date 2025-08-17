package com.ms.ws.controller;

import com.ms.ws.model.request.AddScheduleRequest;
import com.ms.ws.model.request.SwapAssigneeRequest;
import com.ms.ws.service.WeekendSupportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weekend-support")
public class WeekendSupportController {

  @Autowired
  WeekendSupportService supportService;

  @GetMapping("/generate-schedule")
  public ResponseEntity<?> test() {
    return supportService.generateSchedule();
  }

  @GetMapping("/fetch-schedule")
  public ResponseEntity<?> fetchSchedule() {
    return supportService.fetchSchedule();
  }

  @DeleteMapping("/delete-schedules")
  public ResponseEntity<?> deleteAssignee(
      @RequestHeader(name = "userId") String userId,
      @RequestBody List<String> scheduleDates) {
    return supportService.deleteSchedule(userId, scheduleDates);
  }

  @GetMapping("/clear-all")
  public ResponseEntity<Boolean> clearAll(
      @RequestHeader(name = "userId") String userId,
      @RequestParam( required = false) String collectionName) {
    return supportService.clearAllDocuments(userId, collectionName);
  }

  @PostMapping("/add-assignee")
  public ResponseEntity<?> addSchedule(
      @RequestHeader(name = "userId") String userId,
      @RequestBody AddScheduleRequest addScheduleRequest) {
    return supportService.addAssignee(userId, addScheduleRequest);
  }

  @PostMapping("/swap-assignee")
  public ResponseEntity<?> swapAssignee(
      @RequestHeader(name = "userId") String userId,
      @RequestBody SwapAssigneeRequest request) {
    return supportService.swapAssignee(userId, request);

  }

  @GetMapping("/activity-logs")
  public ResponseEntity<?> getActivityLogs(
      @RequestHeader(name = "userId", required = false) String userId,
      @RequestParam( required = false)  String date ) {

    return supportService.getActivityLogs(userId,date);
  }

  @GetMapping("/assignee-report")
  public ResponseEntity<?> getAssigneeReport(@RequestParam(required = false) String date) {
    return supportService.getAssigneeReport();
  }

}
