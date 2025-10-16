package com.greciabueno.todo_app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Getter
@Setter
//entities must have a no-args constructor that is public or protected
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String task;

    @Column(nullable = false)
    private boolean completed = false;

    public Task(String task, boolean completed) {
        this.task = task;
        this.completed = completed;
    }
}

