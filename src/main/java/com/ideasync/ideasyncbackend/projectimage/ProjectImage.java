package com.ideasync.ideasyncbackend.projectimage;
import com.ideasync.ideasyncbackend.project.Project;

import jakarta.persistence.*;

@Entity
@Table(name = "projectimage")
public class ProjectImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "projectId", nullable = false)
  private Project project;

  @Column(name = "imageUrl")
  private String imageUrl;

  // Constructors, getters, and setters

  public Long getId() {
    return id;
  }

  public Project getProject() {
    return project;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}

