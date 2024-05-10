package com.ideasync.ideasyncbackend.project;

import com.ideasync.ideasyncbackend.project.dto.ProjectRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
    private final ProjectService projectService;
    Map<String, String> env = System.getenv();

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public String createProject(@RequestBody ProjectRequest projectRequest) {
        return projectService.createProject(projectRequest);
    }
}

