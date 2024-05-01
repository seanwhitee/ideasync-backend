package com.ideasync.ideasyncbackend.project;

import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping

public class ProjectController {
    private final ProjectService projectService;
    Map<String, String> env = System.getenv();
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @PostMapping("/createProject")
    public String saveProject(@RequestBody Project project){return projectService.CreateProject(project);}

    @PostMapping("/saveProjectData")
    public String saveProjectData(@RequestBody Project project){return projectService.saveProjectData(project);}
}

