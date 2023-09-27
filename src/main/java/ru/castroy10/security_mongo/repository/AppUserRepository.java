package ru.castroy10.security_mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.castroy10.security_mongo.model.AppUser;

public interface AppUserRepository extends MongoRepository<AppUser, String> {

    @Query("{username:'?0'}")
    AppUser findUserByUsername(String username);
}
