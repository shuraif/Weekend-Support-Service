package com.ms.ws.repo;

import com.ms.ws.model.entity.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo  extends MongoRepository<User, String> {

  @Query("{email:'?0', passwordHash:'?1'}")
  User findItemByEmailAndPassword(String email, String passwordHash);

  @Query(value = "{email:'?0'}", fields = "{ password: 0 }")
  User findByEmail(String email);

  @Query(value = "{id:'?0'}", fields = "{ password: 0 }")
  User findByUSerId(String userId);

  @Query(value = "{ team: ?0 }", fields = "{ passwordHash: 0 }")
  List<User> findAllByTeam(String team);
}
