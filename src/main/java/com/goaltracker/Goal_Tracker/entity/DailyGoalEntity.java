package com.goaltracker.Goal_Tracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class DailyGoalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_goal_id")
    private Long id;
    @NonNull
    private String title;
    private String description;
    private LocalTime start_time;
    private LocalTime end_time;
    private Date date;
    private float duration_in_hours;
    private boolean completed;


    @ManyToOne
    @JoinColumn(name = "main_goal_id")
    private MainGoalEntity goal;
}
