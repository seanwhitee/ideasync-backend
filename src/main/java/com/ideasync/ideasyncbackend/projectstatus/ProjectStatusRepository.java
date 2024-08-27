package com.ideasync.ideasyncbackend.projectstatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, UUID> {
    ProjectStatus findByStatus(String status);
}
