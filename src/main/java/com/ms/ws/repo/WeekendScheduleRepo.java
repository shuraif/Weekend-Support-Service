package com.ms.ws.repo;

import com.ms.ws.model.entity.WeekendSchedule;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekendScheduleRepo extends MongoRepository<WeekendSchedule, String> {

    WeekendSchedule findByDate(String date);

    void deleteByDate(String date);

    WeekendSchedule getById(String currentId);

    List<WeekendSchedule> findAllByDateIn(List<LocalDate> datesToSwap);

    void deleteAllByDateIn(List<LocalDate> scheduleDates);

    @Query(value = "{}", fields = "{'primary': 1, 'secondary': 1}", count = true)
    Map<String, Integer> getScheduleCountByUser();

    List<WeekendSchedule> findByDateBefore(LocalDate now);

    // Between two dates
    List<WeekendSchedule> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // After a specific date
    List<WeekendSchedule> findByDateAfter(LocalDate date);

    // Sorted results
    List<WeekendSchedule> findByDateBeforeOrderByDateDesc(LocalDate date);

    // Find all schedules before or equal to a specific date
    List<WeekendSchedule> findByDateLessThanEqual(LocalDate date);
}
