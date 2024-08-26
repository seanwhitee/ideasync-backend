package com.ideasync.ideasyncbackend.projectstatus;

import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/projectStatus")
public class ProjectStatusController {
    private final ProjectStatusService projectStatusService;

    public ProjectStatusController(ProjectStatusService projectStatusService){
        this.projectStatusService = projectStatusService;
    }

    @GetMapping("/getRecommendProjectsByStatus")
    public List<ProjectResponse> getRecommendProjectsByStatus(@RequestParam String status, @RequestParam UUID userId) {
        return projectStatusService.getRecommendProjectsByStatus(status, userId);
    }
}
