package com.limonnana.generic.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.limonnana.generic.entities.UserSession;

@Repository
public interface UserSessionRepository extends MongoRepository<UserSession, String> {

}
