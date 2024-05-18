package com.ideasync.ideasyncbackend.projectstatus;

import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
    ProjectStatus findStatusById(long id);
    ProjectStatus findByStatus(String status);
}
