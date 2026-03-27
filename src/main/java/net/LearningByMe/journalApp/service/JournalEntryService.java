package net.LearningByMe.journalApp.service;

import net.LearningByMe.journalApp.Repository.JournalEntryRepository;

import net.LearningByMe.journalApp.entity.JournalEntity;
import net.LearningByMe.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntity journalEntity, String userName){
        try{
            User user = userService.findByUserName(userName);
            journalEntity.setDate(LocalDateTime.now());
            JournalEntity saved = journalEntryRepository.save(journalEntity);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error comes ",e);
        }

    }

    public void saveEntry(JournalEntity journalEntity){
        journalEntryRepository.save(journalEntity);
    }

    public List<JournalEntity> findAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }


    public JournalEntity updateEntry(ObjectId id, JournalEntity newEntry){
        JournalEntity old = journalEntryRepository.findById(id).orElse(null);
        if(old != null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle(): old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent(): old.getContent());
            journalEntryRepository.save(old);
        }
        return old;
    }
}
