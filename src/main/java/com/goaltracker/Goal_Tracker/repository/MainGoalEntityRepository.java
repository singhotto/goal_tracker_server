package com.goaltracker.Goal_Tracker.repository;

import com.goaltracker.Goal_Tracker.entity.MainGoalEntity;
import com.goaltracker.Goal_Tracker.projection.MainGoalTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainGoalEntityRepository extends JpaRepository<MainGoalEntity, Long> {
    List<MainGoalTitle> findAllBy();
}
