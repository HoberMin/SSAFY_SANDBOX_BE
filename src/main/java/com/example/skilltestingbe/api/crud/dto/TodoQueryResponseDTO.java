package com.example.skilltestingbe.api.crud.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TodoQueryResponseDTO {

    @ArraySchema(schema = @Schema(implementation = TodoVO.class))
    private List<TodoVO> todos;
}
