package com.goaltracker.Goal_Tracker.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class DailyGoalWithMainIdDto {
    private Long id;
    private String title;
    private String description;
    private LocalTime start_time;
    private LocalTime end_time;
    private Date date;
    private float duration_in_hours;
    private boolean completed;
    private Long main_goal_id;
}
