package com.example.skilltestingbe.api.crud.presentation;

import com.example.skilltestingbe.api.crud.dto.TodoDTO;
import com.example.skilltestingbe.api.crud.dto.TodoQueryResponseDTO;
import com.example.skilltestingbe.api.crud.dto.TodoVO;
import com.example.skilltestingbe.api.crud.service.CRUDService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
@Tag(name = "CRUD API", description = "CRUD 페이지 API")
public class CRUDController {

    private final CRUDService crudService;

    @GetMapping()
    @Operation(summary = "Todo 조회", description = "일정 목록 조회")
    @ApiResponse(responseCode = "200", description = "데이터 베이스에 있는 일정을 조회합니다.",
            content = @Content(
                    schema = @Schema(implementation = TodoQueryResponseDTO.class, description = "일정의 List")
    ))
    public ResponseEntity<?> getTodos() {
        List<TodoVO> todos = crudService.findAll();
        TodoQueryResponseDTO result = new TodoQueryResponseDTO(todos);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping()
    @Operation(summary = "Todo 추가", description = "일정 추가")
    @ApiResponse(responseCode = "201", description = "데이터 베이스에 일정을 추가합니다.")
    public ResponseEntity<?> addTodo(@RequestBody TodoDTO body) {
        String content = body.getContent();
        crudService.save(content);
        return ResponseEntity.created(null).build();
    }

    @DeleteMapping("/{todoId}")
    @Operation(summary = "Todo 조회", description = "일정 삭제")
    @ApiResponse(responseCode = "204", description = "todo id에 해당하는 일정을 삭제합니다.")
    public ResponseEntity<?> deleteTodo(@PathVariable long todoId) {
        crudService.deleteById(todoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{todoId}")
    @Operation(summary = "Todo 갱신", description = "일정의 상태 갱신")
    @ApiResponse(responseCode = "201", description = "todo id에 해당하는 일정의 status 를 변경합니다.")
    public ResponseEntity<?> updateTodo(@PathVariable long todoId) {
        try {
            crudService.updateTodo(todoId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.created(null).build();
    }
}
