package com.ideasync.ideasyncbackend.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long>{
    Project findByHostUserId(String hostUserId);
    Project findBySchool(String school);
    Project findByDescription(String description);
}
