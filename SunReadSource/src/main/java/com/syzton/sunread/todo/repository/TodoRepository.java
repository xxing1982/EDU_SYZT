package com.syzton.sunread.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.todo.model.Todo;

/**
 * @author Petri Kainulainen
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
