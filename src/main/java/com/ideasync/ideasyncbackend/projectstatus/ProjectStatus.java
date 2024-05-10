package com.ideasync.ideasyncbackend.projectstatus;

import com.ideasync.ideasyncbackend.project.Project;
import jakarta.persistence.*;
import org.apache.juli.logging.Log;

import java.util.List;

@Entity
@Table(name = "projectstatus")
public class ProjectStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "status", nullable = false)
  private String status;

  @OneToMany(mappedBy = "projectStatus", cascade = {CascadeType.MERGE})
  private List<Project> projects;
  // Constructors, getters, and setters

  public Long getId() {
    return id;
  }

  public String getStatus() {
    return status;
  }

  public List<Project> getProjects() {
    return projects;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }
}

