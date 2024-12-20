package com.mysite.responseentitytoyproject.repository;


import com.mysite.responseentitytoyproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

