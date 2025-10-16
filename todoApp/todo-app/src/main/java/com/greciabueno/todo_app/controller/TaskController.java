package com.greciabueno.todo_app.controller;

import com.greciabueno.todo_app.model.Task;
import com.greciabueno.todo_app.service.TaskService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3001", "http://backend:3000"})
@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService service;

    @GetMapping("/getAllTasks")
    public ResponseEntity<List<Task>> getAll() {
        List<Task> tasks = service.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/add")
    public ResponseEntity<Task> create(
            @RequestBody Task task,
            UriComponentsBuilder uriBuilder) {

        Task saved = service.add(task);

        //assign location header pointing to where the new resource lives
        var uri = uriBuilder
                .path("/tasks/add/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    @PatchMapping("/{id}/completed")
    public ResponseEntity<Task> markComplete(
            @PathVariable Long id) {

        //catching the exception and sending a 404 not found error
        try {
            Task updated = service.markCompleted(id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }




        //******************************************************************
                //In case we wanted to un-complete the already completed task
        //        Boolean completed = body.get("completed");
        //        if(completed == null) {
        //            return ResponseEntity.badRequest().build();
        //        }
        //
        //        try {
        //            Task updated = service.markCompleted(id, completed);
        //            return ResponseEntity.ok(updated);
        //        } catch (RuntimeException e) {
        //            return ResponseEntity.notFound().build();
        //        }
        //***************************************************************************
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        //call the service layer
        boolean itExists = service.delete(id);

        //return the response entity according to the service return
        if(itExists) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
