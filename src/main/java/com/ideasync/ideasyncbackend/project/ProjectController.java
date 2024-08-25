package com.ideasync.ideasyncbackend.project;

import com.ideasync.ideasyncbackend.project.dto.ProjectRequest;
import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/search")
    public List<ProjectResponse> searchProject(@RequestParam String searchString) {
        return projectService.searchProject(searchString);
    }

    @GetMapping("/getProjectsByUser")
    public List<ProjectResponse> getProjectsByUser(@RequestParam UUID userId) {
        return projectService.getProjectsByUser(userId);
    }

    @DeleteMapping("/delete")
    public String deleteProject(@RequestParam UUID id) {
        return projectService.deleteProjectById(id);
    }


    @GetMapping("/getProjectById")
    public ProjectResponse getProjectById(@RequestParam UUID id) {
        return projectService.getProjectById(id);
    }

    @GetMapping("/getRelatedProjects")
    public List<ProjectResponse> getRelatedProjects(@RequestParam UUID projectId) {
        return projectService.getRelatedProjects(projectId);
    }

    @PostMapping("/changeProjectStatus")
    public ProjectResponse changeProjectStatus(@RequestParam UUID projectId,
                                               @RequestParam String status,
                                               @RequestParam String nextOrPrevious) {
        return projectService.changeProjectStatus(projectId, status, nextOrPrevious);
    }
}

