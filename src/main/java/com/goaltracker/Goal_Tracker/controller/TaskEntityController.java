package com.goaltracker.Goal_Tracker.controller;

import com.goaltracker.Goal_Tracker.dto.DailyGoalDto;
import com.goaltracker.Goal_Tracker.entity.TaskEntity;
import com.goaltracker.Goal_Tracker.service.TaskEntityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("task")
public class TaskEntityController {
    @Autowired
    TaskEntityService service;

    @GetMapping
    public List<TaskEntity> getAll(){
        return service.getAll();
    }

    @GetMapping("/id/{myId}")
    public  ResponseEntity<TaskEntity> getId(@PathVariable Long myId){
        Optional<TaskEntity> ge = service.getGoalById(myId);
        if(ge.isPresent()){
            return new ResponseEntity<>(ge.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<TaskEntity> createEntry(@RequestBody TaskEntity entry){
        Optional<TaskEntity> je = service.saveGoal(entry);
        if(je.isPresent()){
            return new ResponseEntity<>(je.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGoal(@PathVariable Long id) {
        try {
            service.deleteGoalById(id);
            return ResponseEntity.ok("Task with id " + id + " deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the Task.");
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<TaskEntity> updateGoal(@PathVariable Long id, @RequestBody TaskEntity goal){
        // Ensure the ID in the URL and body match
        if (id == null || !id.equals(goal.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Optional<TaskEntity> je = service.updateGoal(id, goal);
        if(je.isPresent()){
            return new ResponseEntity<>(je.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
