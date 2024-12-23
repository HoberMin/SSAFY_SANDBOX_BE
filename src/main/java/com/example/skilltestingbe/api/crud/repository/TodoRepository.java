package com.example.skilltestingbe.api.crud.repository;

import com.example.skilltestingbe.api.crud.dto.TodoVO;
import com.example.skilltestingbe.api.crud.repository.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo,Long> {

    List<TodoVO> findAllProjectsBy();

    @Override
    Todo save(Todo todo);

    @Override
    void deleteById(Long todoId);

    @Override
    Optional<Todo> findById(Long todoId);
}
