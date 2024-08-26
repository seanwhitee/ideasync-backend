package com.ideasync.ideasyncbackend.projectimage;

import com.ideasync.ideasyncbackend.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectImageRepository extends JpaRepository<ProjectImage, UUID> {
    List<ProjectImage>  findProjectImagesByProject(Project project);
}
