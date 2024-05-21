package com.ideasync.ideasyncbackend.projectstatus;

import com.ideasync.ideasyncbackend.project.ProjectService;
import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import com.ideasync.ideasyncbackend.projectimage.ProjectImageRepository;
import com.ideasync.ideasyncbackend.projectstatus.dto.ProjectStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.project.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectStatusService {
    private final ProjectStatusRepository projectStatusRepository;
    private final ProjectService projectService;
    private final ProjectImageRepository projectImageRepository;

    @Autowired
    public ProjectStatusService(ProjectStatusRepository projectStatusRepository,
                                ProjectRepository projectRepository,
                                ProjectService projectService,
                                ProjectImageRepository projectImageRepository){
        this.projectStatusRepository = projectStatusRepository;
        this.projectService = projectService;
        this.projectImageRepository = projectImageRepository;
    }
    private ProjectStatusResponse getProjectStatusResponse(ProjectStatus projectStatusData) {
        ProjectStatusResponse response = new ProjectStatusResponse();

        response.setId(projectStatusData.getId());
        response.setStatus(projectStatusData.getStatus());

        List<ProjectResponse> projectResponses = new ArrayList<>();
        for (Project project : projectStatusData.getProjects()) {
            ProjectResponse projectResponse = projectService.setProjectResponse(project);
            projectResponses.add(projectResponse);
        }

        response.setProjects(projectResponses);

        return response;
    }

    public List<ProjectStatusResponse> getProjectStatusForMembers() {
        return getProjectStatus(1);
    }

    public List<ProjectStatusResponse> getProjectStatusForTeachers() {
        return getProjectStatus(2);
    }

    public List<ProjectStatusResponse> getProjectStatus(long id) {
        List<ProjectStatus> projects = projectStatusRepository.findAll();
        List<ProjectStatusResponse> projectStatusResponses = new ArrayList<>();
        for (ProjectStatus projectStatus : projects) {
            if (projectStatus.getId() == id) {
                projectStatusResponses.add(getProjectStatusResponse(projectStatus));
            }
        }
        return projectStatusResponses;
    }
}
