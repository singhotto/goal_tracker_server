package com.goaltracker.Goal_Tracker.service;

import com.goaltracker.Goal_Tracker.dto.MainGoalDto;
import com.goaltracker.Goal_Tracker.entity.MainGoalEntity;
import com.goaltracker.Goal_Tracker.entity.TaskEntity;
import com.goaltracker.Goal_Tracker.repository.TaskEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskEntityService {
    @Autowired
    private TaskEntityRepository repository;

    public List<TaskEntity> getAll(){
        return repository.findAll();
    }

    public Optional<TaskEntity> getGoalById(Long Id){
        return repository.findById(Id);
    }

    public Optional<TaskEntity> saveGoal(TaskEntity goal){
        return Optional.of(repository.save(goal));
    }

    @Transactional
    public void deleteGoalById(Long id) {
        Optional<TaskEntity> goal = repository.findById(id);
        if (goal.isPresent()) {
            repository.delete(goal.get());
        } else {
            throw new EntityNotFoundException("TaskEntity with id " + id + " not found.");
        }
    }


    @Transactional
    public Optional<TaskEntity> updateGoal(Long id, TaskEntity task) {
        // Check if the DailyGoalEntity exists
        Optional<TaskEntity> existingEntityOpt = repository.findById(id);
        if (existingEntityOpt.isEmpty()) {
            return Optional.empty();
        }

        TaskEntity existingEntity = existingEntityOpt.get();

        // Update the fields of the existing entity
        existingEntity.setTitle(task.getTitle());
        existingEntity.setDescription(task.getDescription());
        existingEntity.setDate(task.getDate());
        existingEntity.setCompleted(task.isCompleted());
        existingEntity.setStart_time(task.getStart_time());
        existingEntity.setEnd_time(task.getEnd_time());

        // Save the updated entity
        return Optional.of(repository.save(existingEntity));
    }
}