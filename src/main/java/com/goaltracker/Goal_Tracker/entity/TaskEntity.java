package com.goaltracker.Goal_Tracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @NonNull
    private String title;
    private String description;
    private LocalTime start_time;
    private LocalTime end_time;
    private Date date;
    private float duration_in_hours;
    private boolean completed;
}
