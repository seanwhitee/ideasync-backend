package com.ideasync.ideasyncbackend.tag;

import com.ideasync.ideasyncbackend.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findTagsByProject(Project project);
}
