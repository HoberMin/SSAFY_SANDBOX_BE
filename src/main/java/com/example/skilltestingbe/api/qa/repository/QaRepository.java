package com.example.skilltestingbe.api.qa.repository;

import com.example.skilltestingbe.api.qa.repository.domain.Qa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QaRepository extends JpaRepository<Qa, Long> {
}
