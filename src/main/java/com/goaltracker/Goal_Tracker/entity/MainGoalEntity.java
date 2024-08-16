package com.goaltracker.Goal_Tracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class MainGoalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_goal_id")
    private Long id;
    private String title;
    private LocalDate start_date;
    private LocalDate end_date;
    private List<String> work_days;
    private float duration_in_hours;
    private boolean urgent;
    private boolean important;
    private boolean completed;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DailyGoalEntity> goals;
}
