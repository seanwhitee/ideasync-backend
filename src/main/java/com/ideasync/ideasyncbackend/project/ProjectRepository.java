package com.ideasync.ideasyncbackend.project;

import com.ideasync.ideasyncbackend.projectstatus.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideasync.ideasyncbackend.user.User;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID>{
    Project findProjectById(UUID id);
    Project findProjectByUser(User user);
    List<Project> findProjectsByUser(User user);
    Project findBySchool(String school);
    Project findByDescription(String description);
    List<Project> findProjectByProjectStatus(ProjectStatus projectStatus);
    List<Project> findProjectsByTitleContaining(String searchString);
}
