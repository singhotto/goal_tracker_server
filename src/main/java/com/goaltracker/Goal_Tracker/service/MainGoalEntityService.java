package com.goaltracker.Goal_Tracker.service;

import com.goaltracker.Goal_Tracker.dto.MainGoalDto;
import com.goaltracker.Goal_Tracker.entity.DailyGoalEntity;
import com.goaltracker.Goal_Tracker.entity.MainGoalEntity;
import com.goaltracker.Goal_Tracker.projection.MainGoalTitle;
import com.goaltracker.Goal_Tracker.repository.MainGoalEntityRepository;
import jakarta.persistence.EntityManager;
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
    @Autowired
    private EntityManager entityManager;

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

    @Transactional
    public Optional<MainGoalDto> updateGoal(Long id, MainGoalDto goal) {
        // Check if the DailyGoalEntity exists
        Optional<MainGoalEntity> existingEntityOpt = repository.findById(id);
        if (existingEntityOpt.isEmpty()) {
            return Optional.empty();
        }

        MainGoalEntity existingEntity = existingEntityOpt.get();

        // Update the fields of the existing entity
        existingEntity.setTitle(goal.getTitle());
        existingEntity.setStart_date(goal.getStart_date());
        existingEntity.setEnd_date(goal.getEnd_date());
        existingEntity.setDuration_in_hours(goal.getDuration_in_hours());
        existingEntity.setCompleted(goal.isCompleted());
        existingEntity.setWork_days(goal.getWork_days());
        existingEntity.setUrgent(goal.isUrgent());
        existingEntity.setImportant(goal.isImportant());

        // Save the updated entity
        MainGoalEntity updatedEntity = repository.save(existingEntity);

        // Convert the updated entity to a DTO
        MainGoalDto updatedDto = new MainGoalDto();
        updatedDto.setTitle(updatedEntity.getTitle());
        updatedDto.setStart_date(updatedEntity.getStart_date());
        updatedDto.setEnd_date(updatedEntity.getEnd_date());
        updatedDto.setDuration_in_hours(updatedEntity.getDuration_in_hours());
        updatedDto.setCompleted(updatedEntity.isCompleted());
        updatedDto.setWork_days(updatedEntity.getWork_days());
        updatedDto.setUrgent(updatedEntity.isUrgent());
        updatedDto.setImportant(updatedEntity.isImportant());

        return Optional.of(updatedDto);
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
