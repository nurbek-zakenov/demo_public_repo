package com.example.iasback.models;

import lombok.Data;

import java.time.LocalDate;


@Data
public class Event {
    int id;
    String name;
    LocalDate start;
    LocalDate end;
    boolean completed;
    String description;
}
