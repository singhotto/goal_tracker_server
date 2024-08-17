package com.goaltracker.Goal_Tracker.controller;

import com.goaltracker.Goal_Tracker.dto.DailyGoalDto;
import com.goaltracker.Goal_Tracker.dto.MainGoalDto;
import com.goaltracker.Goal_Tracker.entity.MainGoalEntity;
import com.goaltracker.Goal_Tracker.projection.MainGoalTitle;
import com.goaltracker.Goal_Tracker.service.MainGoalEntityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("main_goal")
public class MainGoalEntityController {
    @Autowired
    MainGoalEntityService service;

    @GetMapping
    public List<MainGoalDto> getAll(){
        return service.getAll();
    }

    @GetMapping("/titles")
    public List<MainGoalTitle> getAllTitles() {
        return service.getAllTitles();
    }

    @GetMapping("/id/{myId}")
    public  ResponseEntity<MainGoalEntity> getId(@PathVariable Long myId){
        Optional<MainGoalEntity> ge = service.getGoalById(myId);
        if(ge.isPresent()){
            return new ResponseEntity<>(ge.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<MainGoalEntity> createEntry(@RequestBody MainGoalEntity entry){
        Optional<MainGoalEntity> je = service.saveGoal(entry);
        if(je.isPresent()){
            return new ResponseEntity<>(je.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGoal(@PathVariable Long id) {
        try {
            service.deleteGoalById(id);
            return ResponseEntity.ok("MainGoalEntity with id " + id + " deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting id "+ id +" the MainGoalEntity.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MainGoalDto> updateGoal(@PathVariable Long id, @RequestBody MainGoalDto goal){
        // Ensure the ID in the URL and body match
        if (id == null || !id.equals(goal.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Optional<MainGoalDto> je = service.updateGoal(id, goal);
        if(je.isPresent()){
            return new ResponseEntity<>(je.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
