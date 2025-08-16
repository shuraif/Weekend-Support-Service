package com.ms.ws.repo;

import com.ms.ws.model.entity.ActivityLog;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ActivityLogRepo extends MongoRepository<ActivityLog,String> {

  @Query(value = "{'date': ?0}", sort = "{'timestamp': -1}")
  List<ActivityLog> findAllByDateDesc(LocalDate localDate);

  @Query(value = "{}", sort = "{ timestamp : -1 }")
  List<ActivityLog> findAllByDesc();
}
