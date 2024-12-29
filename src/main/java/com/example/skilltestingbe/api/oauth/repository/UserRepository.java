package com.example.skilltestingbe.api.oauth.repository;

import com.example.skilltestingbe.api.oauth.repository.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
