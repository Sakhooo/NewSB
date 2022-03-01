package com.example.newsb.lesson2.modul;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {
    private Long id;
    private String name;
    private String description;
    private String deadlineDate;
    private boolean isCompleted;
}
