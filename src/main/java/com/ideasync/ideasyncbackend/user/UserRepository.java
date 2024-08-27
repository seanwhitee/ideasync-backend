package com.ideasync.ideasyncbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
  User findUserById(UUID id);
  User findByUserName(String userName);
  User findByEmail(String email);
}
