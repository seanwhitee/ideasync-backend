package com.ideasync.ideasyncbackend.projectstatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
    ProjectStatus findStatusById(long id);
    ProjectStatus findByStatus(String status);
}
