package com.ideasync.ideasyncbackend.archive;

import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, UUID> {
    Archive findArchiveByProjectAndUser(Project project, User user);
    List<Archive> findArchivesByUserId(UUID userId);
    List<Archive> findArchivesByProject(Project project);
}
