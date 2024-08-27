package com.ideasync.ideasyncbackend.tag;

import com.ideasync.ideasyncbackend.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    List<Tag> findTagsByProject(Project project);
}
