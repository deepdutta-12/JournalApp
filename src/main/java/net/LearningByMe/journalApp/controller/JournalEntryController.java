package net.LearningByMe.journalApp.controller;

import net.LearningByMe.journalApp.entity.JournalEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntity> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntity> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntity myEntry){
        journalEntries.put(myEntry.getId(), myEntry);
        return true;
    }

    @GetMapping("id/{myId}")
    public JournalEntity getJournalEntryById(@PathVariable long myId){
        return journalEntries.get(myId);
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable long myId){
        journalEntries.remove(myId);
        return true;
    }

    @PutMapping("id/{myId}")
    public JournalEntity updateJournalEntityById(@PathVariable long myId, @RequestBody JournalEntity myEntry){
        return  journalEntries.put(myId, myEntry);
    }
}
