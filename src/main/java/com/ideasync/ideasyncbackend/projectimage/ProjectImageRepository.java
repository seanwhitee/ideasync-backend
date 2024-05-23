package com.ideasync.ideasyncbackend.projectimage;

import com.ideasync.ideasyncbackend.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {
    List<ProjectImage>  findProjectImagesByProject(Project project);
}
