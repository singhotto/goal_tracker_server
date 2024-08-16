package com.goaltracker.Goal_Tracker.service;

import com.goaltracker.Goal_Tracker.dto.DailyGoalDto;
import com.goaltracker.Goal_Tracker.dto.DailyGoalWithMainIdDto;
import com.goaltracker.Goal_Tracker.entity.DailyGoalEntity;
import com.goaltracker.Goal_Tracker.entity.MainGoalEntity;
import com.goaltracker.Goal_Tracker.repository.DailyGoalEntityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DailyGoalEntityService {
    @Autowired
    private DailyGoalEntityRepository repository;

    @Autowired
    private EntityManager entityManager;

    public List<DailyGoalDto> getAll(){
        return repository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public Optional<DailyGoalEntity> getGoalById(Long Id){
        return repository.findById(Id);
    }

    public Optional<DailyGoalWithMainIdDto> saveGoal(DailyGoalWithMainIdDto goal){
        try{
            MainGoalEntity mainGoalReference = entityManager.getReference(MainGoalEntity.class, goal.getMain_goal_id());
            DailyGoalEntity dailyGoalEntity = new DailyGoalEntity();
            dailyGoalEntity.setGoal(mainGoalReference);
            dailyGoalEntity.setId(goal.getId());
            dailyGoalEntity.setTitle(goal.getTitle());
            dailyGoalEntity.setDate(goal.getDate());
            dailyGoalEntity.setDescription(goal.getDescription());
            dailyGoalEntity.setStart_time(goal.getStart_time());
            dailyGoalEntity.setEnd_time(goal.getEnd_time());
            dailyGoalEntity.setDuration_in_hours(goal.getDuration_in_hours());
            dailyGoalEntity.setCompleted(goal.isCompleted());
            repository.save(dailyGoalEntity);
            return Optional.of(goal);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public void deleteGoalById(Long id) {
        Optional<DailyGoalEntity> goal = repository.findById(id);
        if (goal.isPresent()) {
            repository.delete(goal.get());
        } else {
            throw new EntityNotFoundException("DailyGoalEntity with id " + id + " not found.");
        }
    }

    @Transactional
    public Optional<DailyGoalDto> updateGoal(Long id, DailyGoalDto goal) {
        // Check if the DailyGoalEntity exists
        Optional<DailyGoalEntity> existingEntityOpt = repository.findById(id);
        if (existingEntityOpt.isEmpty()) {
            return Optional.empty();
        }

        DailyGoalEntity existingEntity = existingEntityOpt.get();
        MainGoalEntity mainGoalReference = entityManager.getReference(MainGoalEntity.class, goal.getMain_goal_id());

        // Update the fields of the existing entity
        existingEntity.setTitle(goal.getTitle());
        existingEntity.setDescription(goal.getDescription());
        existingEntity.setStart_time(goal.getStart_time());
        existingEntity.setEnd_time(goal.getEnd_time());
        existingEntity.setDate(goal.getDate());
        existingEntity.setDuration_in_hours(goal.getDuration_in_hours());
        existingEntity.setCompleted(goal.isCompleted());
        existingEntity.setGoal(mainGoalReference);

        // Save the updated entity
        DailyGoalEntity updatedEntity = repository.save(existingEntity);

        // Convert the updated entity to a DTO
        DailyGoalDto updatedDto = new DailyGoalDto();
        updatedDto.setId(updatedEntity.getId());
        updatedDto.setTitle(updatedEntity.getTitle());
        updatedDto.setDescription(updatedEntity.getDescription());
        updatedDto.setStart_time(updatedEntity.getStart_time());
        updatedDto.setEnd_time(updatedEntity.getEnd_time());
        updatedDto.setDate(updatedEntity.getDate());
        updatedDto.setDuration_in_hours(updatedEntity.getDuration_in_hours());
        updatedDto.setCompleted(updatedEntity.isCompleted());
        updatedDto.setMain_goal_id(updatedEntity.getGoal().getId());

        return Optional.of(updatedDto);
    }

    private DailyGoalDto convertEntityToDto(DailyGoalEntity goal){
        String mainGoalTitle = (goal.getGoal() != null) ? goal.getGoal().getTitle() : null;
        Long mainGoalId = (goal.getGoal() != null) ? goal.getGoal().getId() : null;
        DailyGoalDto dto = new DailyGoalDto();
        dto.setId(goal.getId());
        dto.setTitle(goal.getTitle());
        dto.setDate(goal.getDate());
        dto.setDescription(goal.getDescription());
        dto.setStart_time(goal.getStart_time());
        dto.setEnd_time(goal.getEnd_time());
        dto.setMain_goal(mainGoalTitle);
        dto.setDuration_in_hours(goal.getDuration_in_hours());
        dto.setCompleted(goal.isCompleted());
        dto.setMain_goal_id(mainGoalId);
        return dto;
    }
}
