package com.ideasync.ideasyncbackend.projectimage;
import com.ideasync.ideasyncbackend.project.Project;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "projectimage")
public class ProjectImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreationTimestamp
  @Column(name = "createAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp createAt;

  @ManyToOne
  @JoinColumn(name = "projectId", nullable = false)
  private Project project;

  @Column(name = "imageUrl")
  private String imageUrl;

  // Constructors, getters, and setters

  public Long getId() {
    return id;
  }

  public Timestamp getCreateAt() {
    return createAt;
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

  public void setCreateAt(Timestamp createAt) {
    this.createAt = createAt;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}

