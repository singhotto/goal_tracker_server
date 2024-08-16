package com.goaltracker.Goal_Tracker.controller;

import com.goaltracker.Goal_Tracker.dto.DailyGoalDto;
import com.goaltracker.Goal_Tracker.dto.DailyGoalWithMainIdDto;
import com.goaltracker.Goal_Tracker.entity.DailyGoalEntity;
import com.goaltracker.Goal_Tracker.service.DailyGoalEntityService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("daily_goal")
public class DailyGoalEntityController {
    @Autowired
    DailyGoalEntityService service;

    @GetMapping
    public List<DailyGoalDto> getAll(){
        return service.getAll();
    }

    @GetMapping("/id/{myId}")
    public  ResponseEntity<DailyGoalEntity> getId(@PathVariable Long myId){
        Optional<DailyGoalEntity> ge = service.getGoalById(myId);
        if(ge.isPresent()){
            return new ResponseEntity<>(ge.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<DailyGoalWithMainIdDto> createEntry(@RequestBody DailyGoalWithMainIdDto entry){
        Optional<DailyGoalWithMainIdDto> je = service.saveGoal(entry);
        if(je.isPresent()){
            return new ResponseEntity<>(je.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGoal(@PathVariable Long id) {
        try {
            service.deleteGoalById(id);
            return ResponseEntity.ok("DailyGoalEntity with id " + id + " deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the DailyGoalEntity.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DailyGoalDto> updateGoal(@PathVariable Long id, @RequestBody DailyGoalDto goal){
        // Ensure the ID in the URL and body match
        if (id == null || !id.equals(goal.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Optional<DailyGoalDto> je = service.updateGoal(id, goal);
        if(je.isPresent()){
            return new ResponseEntity<>(je.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
