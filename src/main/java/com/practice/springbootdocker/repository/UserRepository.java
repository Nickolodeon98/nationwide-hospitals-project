package com.practice.springbootdocker.repository;

import com.practice.springbootdocker.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
