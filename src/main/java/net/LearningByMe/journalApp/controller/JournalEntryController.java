package net.LearningByMe.journalApp.controller;

import net.LearningByMe.journalApp.entity.JournalEntity;
import net.LearningByMe.journalApp.entity.User;
import net.LearningByMe.journalApp.service.JournalEntryService;
import net.LearningByMe.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;



@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntity> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity myEntry, @PathVariable String userName){
       try {
           journalEntryService.saveEntry(myEntry, userName);
           return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
       } catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId){

        Optional<JournalEntity> journalEntity = journalEntryService.findById(myId);

        if(journalEntity.isPresent()){
            return new ResponseEntity<>(journalEntity.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

@DeleteMapping("/id/{userName}/{myId}")
public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName){
//    User user = userService.findByUserName(userName);
    journalEntryService.deleteById(myId, userName);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

   @PutMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId myId,
                                                @RequestBody JournalEntity newEntry,
                                                @PathVariable String userName){
        JournalEntity old = journalEntryService.findById(myId).orElse(null);
        if(old != null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.equals("")?newEntry.getTitle(): old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
