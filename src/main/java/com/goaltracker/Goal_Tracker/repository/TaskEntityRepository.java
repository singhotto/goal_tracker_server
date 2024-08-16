package com.goaltracker.Goal_Tracker.repository;

import com.goaltracker.Goal_Tracker.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskEntityRepository extends JpaRepository<TaskEntity, Long> {
}
