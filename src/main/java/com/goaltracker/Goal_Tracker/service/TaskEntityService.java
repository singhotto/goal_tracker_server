package com.goaltracker.Goal_Tracker.service;

import com.goaltracker.Goal_Tracker.entity.TaskEntity;
import com.goaltracker.Goal_Tracker.repository.TaskEntityRepository;
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
}