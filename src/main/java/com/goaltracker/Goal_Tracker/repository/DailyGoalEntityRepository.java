package com.goaltracker.Goal_Tracker.repository;

import com.goaltracker.Goal_Tracker.entity.DailyGoalEntity;
import com.goaltracker.Goal_Tracker.entity.MainGoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyGoalEntityRepository extends JpaRepository<DailyGoalEntity, Long> {
}
