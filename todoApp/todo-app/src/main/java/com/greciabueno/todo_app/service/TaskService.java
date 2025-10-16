package com.greciabueno.todo_app.service;

import com.greciabueno.todo_app.model.Task;
import com.greciabueno.todo_app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repo;

    public Task add(Task task) {
        return repo.save(task);
    }

    public Task markCompleted(Long id) {
        //when the completed button is clicked, the task will become complete with no
        //option to un-complete.  No need to look at the request body because it will
        //automatically be set to true.
        return repo.findById(id)
                .map(task -> {
                    task.setCompleted(true);
                    return repo.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found"));


        //***************************************************************************
        //in case we have the option to un-complete the task once clicking complete.
        //we can implement this logic to look into the request body to see if we are
        //passing a true or a false value.  Request body would look like this:
        //@RequestBody Map<String, Boolean> body
        //        Task task = repo.findById(id)
        //                .orElseThrow(() -> new RuntimeException(("Task not found")));
        //
        //        //check if it's already in the desired state
        //        if(task.isCompleted() == complete) {
        //            return task;
        //        }
        //
        //        //if not, then set complete to new value
        //        task.setCompleted(complete);
        //        return repo.save(task);
        // }
        //***************************************************************************
    }

    public boolean delete(Long id) {
        //return false if the id doesn't exist and true if it exists (was successful)
        if(repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

}
