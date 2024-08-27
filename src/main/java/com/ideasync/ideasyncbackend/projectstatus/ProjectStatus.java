package com.ideasync.ideasyncbackend.projectstatus;

import com.ideasync.ideasyncbackend.project.Project;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projectstatus")
public class ProjectStatus {

  @Id @GeneratedValue
  @JdbcTypeCode(Types.VARCHAR)
  private UUID id;

  @Column(name = "status", nullable = false)
  private String status;

  @OneToMany(mappedBy = "projectStatus", cascade = {CascadeType.MERGE})
  private List<Project> projects;
  // Constructors, getters, and setters

  public UUID getId() {
    return id;
  }

  public String getStatus() {
    return status;
  }

  public List<Project> getProjects() {
    return projects;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }
}

