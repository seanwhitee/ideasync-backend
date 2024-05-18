package com.ideasync.ideasyncbackend.projectstatus;

import com.ideasync.ideasyncbackend.projectstatus.dto.ProjectStatusResponse;
import com.ideasync.ideasyncbackend.user.User;
import com.ideasync.ideasyncbackend.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.project.ProjectRepository;
import com.ideasync.ideasyncbackend.projectstatus.dto.ProjectStatusResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectStatusService {
    private final ProjectStatusRepository projectStatusRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectStatusService(ProjectStatusRepository projectStatusRepository, ProjectRepository projectRepository){
        this.projectStatusRepository = projectStatusRepository;
        this.projectRepository = projectRepository;

    }
    private ProjectStatusResponse getProjectStatusResponse(ProjectStatus projectStatusData) {

        ProjectStatusResponse response = new ProjectStatusResponse();
        response.setId(projectStatusData.getId());
        response.setStatus(projectStatusData.getStatus());
        response.setProjects(projectStatusData.getProjects());

        return response;
    }


    public List<ProjectStatusResponse> getProjectStatus(long id){
        List<ProjectStatus> projects = projectStatusRepository.findAll();
        List<ProjectStatusResponse> projectStatusResponses_findMember = new ArrayList<>();
        List<ProjectStatusResponse> projectStatusResponses_findTeacher = new ArrayList<>();
        for(ProjectStatus projectStatus : projects){
            //ProjectStatus status = projectStatusRepository.findStatusById(id);
            if(projectStatus.getId()==1){
               projectStatusResponses_findMember.add(getProjectStatusResponse(projectStatus));
               return projectStatusResponses_findMember;

            } else if (projectStatus.getId()==2) {
                projectStatusResponses_findTeacher.add(getProjectStatusResponse(projectStatus));
                return projectStatusResponses_findTeacher;

            }
        }
        return null;
    }
}
