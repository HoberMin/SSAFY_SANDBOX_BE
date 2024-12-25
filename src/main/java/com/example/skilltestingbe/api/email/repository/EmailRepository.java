package com.example.skilltestingbe.api.email.repository;

import com.example.skilltestingbe.api.email.repository.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    Optional<Email> findByEmail(String email);
    Optional<Email> findByEmailAndCode(String email, String code);
}
