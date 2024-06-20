package com.ideasync.ideasyncbackend.project;

import com.ideasync.ideasyncbackend.project.dto.ProjectRequest;
import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public String createProject(@RequestBody ProjectRequest projectRequest) {
        return projectService.createProject(projectRequest);
    }

    @DeleteMapping("/delete")
    public String deleteProject(@RequestParam Long id) {
        return projectService.deleteProjectById(id);
    }

    @GetMapping("/getAllProjects")
    public List<ProjectResponse> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/getProjectById")
    public ProjectResponse getProjectById(@RequestParam Long id) {
        return projectService.getProjectById(id);
    }
}

