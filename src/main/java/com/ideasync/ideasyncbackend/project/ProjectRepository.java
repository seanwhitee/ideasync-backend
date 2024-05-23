package com.ideasync.ideasyncbackend.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideasync.ideasyncbackend.user.User;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{
    Project findProjectById(long id);
    Project findProjectByUser(User user);
    List<Project> findProjectsByUser(User user);
    Project findBySchool(String school);
    Project findByDescription(String description);
}
