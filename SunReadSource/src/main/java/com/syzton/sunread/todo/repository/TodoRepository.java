package com.syzton.sunread.todo.repository;

import com.syzton.sunread.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Petri Kainulainen
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
