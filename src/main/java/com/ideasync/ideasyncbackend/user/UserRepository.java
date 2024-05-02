package com.ideasync.ideasyncbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
  User findById(long id);
  User findByUserName(String userName);
  User findByEmail(String email);
}
