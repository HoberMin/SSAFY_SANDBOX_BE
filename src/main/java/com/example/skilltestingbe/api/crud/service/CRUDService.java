package com.example.skilltestingbe.api.crud.service;

import com.example.skilltestingbe.api.crud.dto.TodoVO;
import com.example.skilltestingbe.api.crud.repository.TodoRepository;
import com.example.skilltestingbe.api.crud.repository.domain.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CRUDService {

    private final TodoRepository todoRepository;

    @Transactional(readOnly = true)
    public List<TodoVO> findAll() {
        return todoRepository.findAllProjectsBy();
    }

    public void save(String content) {
        Todo todo = Todo.builder()
                .content(content)
                .build();

        todo.setCompleted(false);
        todoRepository.save(todo);
    }

    public void deleteById(long todoId) {
        todoRepository.deleteById(todoId);
    }

    public void updateTodo(long todoId) throws Exception {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(Exception::new);

        todo.setCompleted(!todo.getCompleted());
    }
}
