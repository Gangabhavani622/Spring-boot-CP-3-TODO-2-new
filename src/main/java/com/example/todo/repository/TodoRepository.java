// Write your code here
package com.example.todo.repository;

import java.util.*;

import com.example.todo.model.Todo;

public interface TodoRepository {
    ArrayList<Todo> getTodos();

    Todo getTodoById(int id);

    Todo addTodo(Todo task);

    Todo updateTodo(int id, Todo task);

    void deleteTodo(int id);

}