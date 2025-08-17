package com.ms.ws.service;

import com.ms.ws.constants.ErrorMessages;
import com.ms.ws.exception.ApplicationException;
import com.ms.ws.model.dto.AssignmentPair;
import com.ms.ws.model.dto.UserScheduleCount;
import com.ms.ws.model.entity.ActivityLog;
import com.ms.ws.model.entity.User;
import com.ms.ws.model.entity.WeekendSchedule;
import com.ms.ws.model.request.AddScheduleRequest;
import com.ms.ws.model.request.SwapAssigneeRequest;
import com.ms.ws.repo.ActivityLogRepo;
import com.ms.ws.repo.UserRepo;
import com.ms.ws.repo.WeekendScheduleRepo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WeekendSupportServiceImpl implements WeekendSupportService {

  Logger logger = LoggerFactory.getLogger(WeekendSupportServiceImpl.class);

  @Autowired
  WeekendScheduleRepo weekendScheduleRepo;

  @Autowired
  UserRepo userRepo;

  @Autowired
  ActivityLogRepo activityLogRepo;

  @Override
  public ResponseEntity<Boolean> clearAllDocuments(String userId, String collectionName) {
    try {

      if (collectionName.equalsIgnoreCase("activityLogs")) {
        activityLogRepo.deleteAll();
      } else if (collectionName.equalsIgnoreCase("users")) {
        userRepo.deleteAll();
        logActivity("CLEAR", "Cleared all users", null);
      } else if (collectionName.equalsIgnoreCase("weekendSchedules")) {
        weekendScheduleRepo.deleteAll();
      } else {
        throw new ApplicationException(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_COLLECTION_NAME);
      }

      logActivity("CLEAR", "Cleared all documents from " + collectionName, userId);
      return new ResponseEntity<>(true, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.SOMETHING_WENT_WRONG);
    }
  }

  @Override
  public ResponseEntity<?> generateSchedule() {

    List<WeekendSchedule> schedule = generateShuffleSchedule();

    return new ResponseEntity<>(schedule, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<?> fetchSchedule() {

    Map<LocalDate, AssignmentPair> schedule = new LinkedHashMap<>();
    LocalDate now = LocalDate.now();

    System.out.println("Before fetch: " + LocalTime.now());
    List<WeekendSchedule> weekendScheduleList = weekendScheduleRepo.findAll();
    if(weekendScheduleList.isEmpty()) {
      logger.info("No weekend schedules found, generating new schedule.");
      weekendScheduleList = generateShuffleSchedule();
    }
    System.out.println("after fetch: " + LocalTime.now());
    for (WeekendSchedule weekendSchedule : weekendScheduleList) {
      if ( null != weekendSchedule.getDate() && weekendSchedule.getDate().isAfter(now.minusYears(1)) && weekendSchedule.getDate().isBefore(now.plusYears(1))) {
        AssignmentPair assignmentPair = new AssignmentPair();
        assignmentPair.setPrimary(weekendSchedule.getPrimary());
        assignmentPair.setSecondary(weekendSchedule.getSecondary());
        schedule.put(weekendSchedule.getDate(), assignmentPair);
      }
    }
    return new ResponseEntity<>(schedule, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<?> swapAssignee(String userId, SwapAssigneeRequest request) {

    if(request.getDatesToSwap() == null || request.getDatesToSwap().size() != 2
        || request.getSupportTypes() == null || request.getSupportTypes().isEmpty()) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_SWAP_REQUEST);
    }

    List<WeekendSchedule> currentSchedules = weekendScheduleRepo.findAllByDateIn(request.getDatesToSwap());

    if (currentSchedules.isEmpty()) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_SWAP_REQUEST);
    }


    WeekendSchedule schedule1;
    WeekendSchedule schedule2;
    schedule1 = currentSchedules.get(0);
    if(currentSchedules.size()==1){
      schedule2 = new WeekendSchedule();
      schedule2.setDate(request.getDatesToSwap().stream().filter(date -> !date.equals(schedule1.getDate())).findFirst()
          .orElseThrow(() -> new ApplicationException(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_SWAP_REQUEST)));
    } else {
      schedule2 = currentSchedules.get(1);
    }

    if(request.getSupportTypes().contains("primary")) {
      User temp = schedule1.getPrimary();
      schedule1.setPrimary(schedule2.getPrimary());
      schedule2.setPrimary(temp);
    }
    if(request.getSupportTypes().contains("secondary")) {
      User temp = schedule1.getSecondary();
      schedule1.setSecondary(schedule2.getSecondary());
      schedule2.setSecondary(temp);
    }


    List<WeekendSchedule> updatedSchedules = Arrays.asList(schedule1, schedule2);
    weekendScheduleRepo.saveAll(updatedSchedules);
    if(schedule1.getPrimary() == null && schedule1.getSecondary() == null) {
      weekendScheduleRepo.delete(schedule1);
    }
    if(schedule2.getPrimary() == null && schedule2.getSecondary() == null) {
      weekendScheduleRepo.delete(schedule2);
    }

    logActivity("SWAP", "Swapped schedules for dates: " + request.getDatesToSwap() +
        ", Assignee Types: " + request.getSupportTypes() , userId);
    return fetchSchedule();
  }

  @Override
  public ResponseEntity<?> addAssignee(String userId, AddScheduleRequest addScheduleRequest) {

    WeekendSchedule schedule = weekendScheduleRepo.findByDate(addScheduleRequest.getDate());
    if (schedule == null) {
      schedule = new WeekendSchedule();
    }
    schedule.setDate(LocalDate.parse(addScheduleRequest.getDate()));
    if(addScheduleRequest.getPrimaryId() != null) {
      User user = userRepo.findByUSerId(addScheduleRequest.getPrimaryId());
      schedule.setPrimary(user);
    }
    if(addScheduleRequest.getSecondaryId() != null) {
      User user = userRepo.findByUSerId(addScheduleRequest.getSecondaryId());
      schedule.setSecondary(user);
    }
    logActivity("ADD", "Added schedule for date: " + addScheduleRequest.getDate() +
        ", Primary: " + (schedule.getPrimary() != null ? schedule.getPrimary().getName() : "None") +
        ", Secondary: " + (schedule.getSecondary() != null ? schedule.getSecondary().getName() : "None"), userId);

    return new ResponseEntity<>(weekendScheduleRepo.save(schedule), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<?> deleteSchedule(String userId, List<String> scheduleDates) {

//    List<WeekendSchedule> schedules = weekendScheduleRepo.findAllByDateIn(scheduleDates);
    List<LocalDate> localDates = scheduleDates.stream().map(LocalDate::parse).toList();
    weekendScheduleRepo.deleteAllByDateIn(localDates);

    logActivity("DELETE","Deleted schedules for dates: " + scheduleDates, userId);
    return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
  }

  @Override
  public ResponseEntity<?> getActivityLogs(String userId, String date) {

    List<ActivityLog> activityLogs;

    if(date != null && !date.isEmpty()) {
      LocalDate localDate = LocalDate.parse(date);
      activityLogs = activityLogRepo.findAllByDateDesc(localDate);
    } else {
      activityLogs = activityLogRepo.findAllByDesc();
    }

    return new ResponseEntity<>(activityLogs, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<?> getAssigneeReport() {

    List<WeekendSchedule> weekendScheduleList = weekendScheduleRepo.findByDateBefore(LocalDate.now());

    List<User> allUsers = userRepo.findAll();

    List<UserScheduleCount> userCounts = allUsers.stream()
        .map(user -> {
          long primaryCount = weekendScheduleList.stream()
              .filter(s -> s.getPrimary() != null && s.getPrimary().getName().equals(user.getName()))
              .count();

          long secondaryCount = weekendScheduleList.stream()
              .filter(s -> s.getSecondary() != null && s.getSecondary().getName().equals(user.getName()))
              .count();

          return new UserScheduleCount(user.getId(), primaryCount, secondaryCount);
        })
        .toList();

    return new ResponseEntity<>(userCounts, HttpStatus.OK);
  }

  public  List<WeekendSchedule> generateShuffleSchedule() {

    List<User> kites = userRepo.findAllByTeam("Kites");
    List<User> denali = userRepo.findAllByTeam("Denali");
    LocalDate currentDate = LocalDate.now();
    LocalDate startOfTheYear = currentDate.withDayOfYear(1);
    System.out.println("Start of the year: " + startOfTheYear);
    Map<LocalDate, AssignmentPair> schedule = new LinkedHashMap<>();


    int kiteIndex1 = 0;
    int denaliIndex1 = 0;

    int kiteIndex2 = 0;
    int denaliIndex2 = 1;

    while( currentDate.isBefore(startOfTheYear.plusYears(2))) {

      if(currentDate.getDayOfWeek().getValue() == 7 || currentDate.getDayOfWeek().getValue() == 6) {
        AssignmentPair assignmentPair = new AssignmentPair();

        if(kiteIndex1 < kites.size()) {
          assignmentPair.setPrimary(kites.get(kiteIndex1));
          kiteIndex1+=1;
        } else if( kiteIndex1 == kites.size() && denaliIndex1 < denali.size()) {
          assignmentPair.setPrimary(denali.get(denaliIndex1));
          denaliIndex1+=1;
        }
        if(denaliIndex1 >= denali.size() && kiteIndex1 == kites.size()) {
          kiteIndex1 = 0;
          denaliIndex1 = 0;
        }

        if(denaliIndex2 < denali.size()) {
          assignmentPair.setSecondary(denali.get(denaliIndex2));
          denaliIndex2+=1;
        } else if( denaliIndex2 == denali.size() && kiteIndex2 <= kites.size()) {
          assignmentPair.setSecondary(kites.get(kiteIndex2));
          kiteIndex2+=1;
        }
        if(kiteIndex2 >= kites.size() && denaliIndex2 == denali.size()) {
          denaliIndex2 = 0;
          kiteIndex2 = 0;
        }

        schedule.put(currentDate, assignmentPair);

      }

      currentDate = currentDate.plusDays(1);
    }

    List<WeekendSchedule> weekendSchedules = new ArrayList<>();
    for(LocalDate date : schedule.keySet()) {
      WeekendSchedule weekendSchedule = new WeekendSchedule();
      weekendSchedule.setDate(date);
      weekendSchedule.setPrimary(schedule.get(date).getPrimary());
      weekendSchedule.setSecondary(schedule.get(date).getSecondary());
      weekendSchedules.add(weekendSchedule);
      if(schedule.get(date).getPrimary() != null && schedule.get(date).getSecondary() != null
          && schedule.get(date).getPrimary().getName().equals(schedule.get(date).getSecondary().getName())) {

        System.out.println("Date: " + date + " Primary: " + schedule.get(date).getPrimary().getName()
            + " Secondary: " + schedule.get(date).getSecondary().getName());
      }
    }
     weekendScheduleRepo.saveAll(weekendSchedules);

    logActivity("GENERATE", "Generated weekend support schedule for the next two years", null);
    return weekendSchedules;
  }

  private void logActivity(String action, String details,String userId) {
    ActivityLog activityLog = new ActivityLog();
    activityLog.setAction(action);
    activityLog.setDetails(details);
    activityLog.setUserId(userId);
    activityLog.setTimestamp(LocalDateTime.now());
    activityLogRepo.save(activityLog);
  }

}
