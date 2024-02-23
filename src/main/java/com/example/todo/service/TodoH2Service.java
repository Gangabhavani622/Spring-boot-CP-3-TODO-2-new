/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.*;
 *
 */

// Write your code here
package com.example.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import com.example.todo.model.Todo;
import com.example.todo.model.TodoRowMapper;
import com.example.todo.repository.TodoRepository;

@Service
public class TodoH2Service implements TodoRepository {

    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Todo> getTodos() {
        List<Todo> todosList = db.query("select * from todoList", new TodoRowMapper());
        ArrayList<Todo> todos = new ArrayList<>(todosList);
        return todos;
    }

    @Override
    public Todo getTodoById(int id) {
        try {
            Todo task = db.queryForObject("select * from todoList where id=?", new TodoRowMapper(), id);
            return task;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public Todo addTodo(Todo task) {
        db.update("insert into todoList(todo, priority, status ) values(?,?,?)", task.getTodo(), task.getPriority(),
                task.getStatus());
        Todo savedTodo = db.queryForObject("select * from todoList where todo=? and priority=? and status=?",
                new TodoRowMapper(), task.getTodo(), task.getPriority(), task.getStatus());
        return savedTodo;
    }

    @Override
    public Todo updateTodo(int id, Todo task) {
        if (task.getTodo() != null) {
            db.update("update todoList set todo=? where id=?", task.getTodo(), id);
        }
        if (task.getPriority() != null) {
            db.update("update todoList set priority=? where id=?", task.getPriority(), id);
        }
        if (task.getStatus() != null) {
            db.update("update todoList set status=? where id=?", task.getStatus(), id);
        }

        return getTodoById(id);

    }

    @Override
    public void deleteTodo(int id) {
        db.update("delete from todoList where id=?", id);

    }

}