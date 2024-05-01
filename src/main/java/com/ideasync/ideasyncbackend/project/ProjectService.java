package com.ideasync.ideasyncbackend.project;

import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    private ProjectResponse getProjectResponse(Project projectData){
        ProjectResponse response = new ProjectResponse();
        response.setId(projectData.getId());
        response.setAllowApplicantsNum(projectData.getAllowApplicantsNum());
        response.setCreateAt(projectData.getCreateAt());
        response.setDescription(projectData.getDescription());
        response.setIsGraduationProject(projectData.isGraduationProject());
        response.setSchool(projectData.getSchool());
        response.setStatusId(projectData.getProjectStatus().getId());
        response.setHostUserId(projectData.getUser().getId());

        return response;
    }
    /**
     * 要確定該填的資料都有填
     * (但我不太確定有沒有漏)
     *
     */
    private boolean verifyProjectData(Project project){
        String description = project.getDescription();
        String school = project.getSchool();
        Long hostUserId = project.getUser().getId();

        if(description == null || school == null){
            return false;
        }
        return true;
    }

    public String saveProjectData(Project project) {
        try {
            ProjectRepository.save(project);
        } catch (Exception e) {
            return "Project created successfully";
        }
        return "Project created failed";
    }
    /**
     * 要確定這個使用者是不是參加別的團隊了
     * (但我不知道怎麼確認這個使用者)
     *
     */
    public String CreateProject(Project project){
        Boolean allowProjectCreate = project.getUser().isAllowProjectCreate();
        if(allowProjectCreate){
            return "You already join project";
        }

    }

}