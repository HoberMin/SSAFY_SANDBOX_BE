package com.example.skilltestingbe.api.crud.repository.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String content;

    @Setter
    @Column(nullable = false)
    private Boolean completed;

    @Builder
    protected Todo(String content) {
        this.content = content;
        this.completed = false;
    }
}
