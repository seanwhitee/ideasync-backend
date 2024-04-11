package com.ideasync.ideasyncbackend.tag;
import jakarta.persistence.*;

import com.ideasync.ideasyncbackend.project.Project;

@Entity
@Table(name = "tag")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "projectId", nullable = false)
  private Project project;

  @Column(nullable = false)
  private String name;

  // Constructors, getters, and setters

  public Long getId() {
    return id;
  }

  public Project getProject() {
    return project;
  }

  public String getName() {
    return name;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setName(String name) {
    this.name = name;
  }
}
