package com.ideasync.ideasyncbackend.projectstatus;

import com.ideasync.ideasyncbackend.projectstatus.dto.ProjectStatusResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/projectStatus")
public class ProjectStatusController {
    private final ProjectStatusService projectStatusService;

    Map<String, String> env = System.getenv();
    ProjectStatusResponse projectStatusResponse = new ProjectStatusResponse();

    public ProjectStatusController(ProjectStatusService projectStatusService){
        this.projectStatusService = projectStatusService;
    }

    @GetMapping("/getProjectStatus")
    public List<ProjectStatusResponse> getProjectStatus(){return projectStatusService.getProjectStatus(projectStatusResponse.getId());}

}
