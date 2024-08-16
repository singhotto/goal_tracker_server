package com.goaltracker.Goal_Tracker.service;

import com.goaltracker.Goal_Tracker.dto.MainGoalDto;
import com.goaltracker.Goal_Tracker.entity.MainGoalEntity;
import com.goaltracker.Goal_Tracker.projection.MainGoalTitle;
import com.goaltracker.Goal_Tracker.repository.MainGoalEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MainGoalEntityService {
    @Autowired
    private MainGoalEntityRepository repository;

    public List<MainGoalDto> getAll() {
        return repository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public Optional<MainGoalEntity> getGoalById(Long Id){
        return repository.findById(Id);
    }

    public Optional<MainGoalEntity> saveGoal(MainGoalEntity goal){
        return Optional.of(repository.save(goal));
    }

    public List<MainGoalTitle> getAllTitles() {
        return repository.findAllBy();
    }

    @Transactional
    public void deleteGoalById(Long id) {
        Optional<MainGoalEntity> goal = repository.findById(id);
        if (goal.isPresent()) {
            repository.delete(goal.get());
        } else {
            throw new EntityNotFoundException("MainGoalEntity with id " + id + " not found.");
        }
    }

    private MainGoalDto convertEntityToDto(MainGoalEntity goal){
        MainGoalDto dto = new MainGoalDto();
        dto.setId(goal.getId());
        dto.setTitle(goal.getTitle());
        dto.setStart_date(goal.getStart_date());
        dto.setEnd_date(goal.getEnd_date());
        dto.setUrgent(goal.isUrgent());
        dto.setWork_days(goal.getWork_days());
        dto.setDuration_in_hours(goal.getDuration_in_hours());
        dto.setImportant(goal.isImportant());
        dto.setCompleted(goal.isCompleted());
        return dto;
    }
}
