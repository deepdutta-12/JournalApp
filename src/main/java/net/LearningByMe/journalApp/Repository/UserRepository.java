package net.LearningByMe.journalApp.Repository;

import net.LearningByMe.journalApp.entity.JournalEntity;
import net.LearningByMe.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);
}
