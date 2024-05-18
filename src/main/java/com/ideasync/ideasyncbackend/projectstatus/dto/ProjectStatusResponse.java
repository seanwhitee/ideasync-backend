package com.ideasync.ideasyncbackend.projectstatus.dto;
import com.ideasync.ideasyncbackend.project.Project;
import java.util.List;

public class ProjectStatusResponse {
    private long id;
    private String status;
    private List<Project> projects;

    public Long getId(){return id;}
    public String getStatus(){return status;}
    public List<Project> getProjects(){return projects;}

    public void setId(long id){this.id = id;}
    public void setStatus(String status){this.status = status;}
    public void setProjects(List<Project> projects){this.projects = projects;}
}
