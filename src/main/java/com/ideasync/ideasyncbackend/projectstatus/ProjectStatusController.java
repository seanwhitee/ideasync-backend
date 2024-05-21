package com.ideasync.ideasyncbackend.projectstatus;

import com.ideasync.ideasyncbackend.projectstatus.dto.ProjectStatusResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/projectStatus")
public class ProjectStatusController {
    private final ProjectStatusService projectStatusService;

    public ProjectStatusController(ProjectStatusService projectStatusService){
        this.projectStatusService = projectStatusService;
    }

    @GetMapping("/getProjectStatusForTeachers")
    public List<ProjectStatusResponse> getProjectStatusForTeachers(){return projectStatusService.getProjectStatusForTeachers();}

    @GetMapping("/getProjectStatusForMembers")
    public List<ProjectStatusResponse> getProjectStatusForMembers(){return projectStatusService.getProjectStatusForMembers();}

}
