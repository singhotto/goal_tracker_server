package com.goaltracker.Goal_Tracker.dto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
public class MainGoalDto {
    private Long id;
    private String title;
    private LocalDate start_date;
    private LocalDate end_date;
    private List<String> work_days;
    private float duration_in_hours;
    private boolean urgent;
    private boolean important;
    private boolean completed;
}
