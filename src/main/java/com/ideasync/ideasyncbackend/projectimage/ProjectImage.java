package com.ideasync.ideasyncbackend.projectimage;
import com.ideasync.ideasyncbackend.project.Project;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "projectimage")
public class ProjectImage {

  @Id @GeneratedValue
  @JdbcTypeCode(Types.VARCHAR)
  private UUID id;

  @CreationTimestamp
  @Column(name = "createAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp createAt;

  @ManyToOne
  @JoinColumn(name = "projectId", nullable = false)
  private Project project;

  @Column(name = "imageUrl")
  private String imageUrl;

  // Constructors, getters, and setters

  public UUID getId() {
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

  public void setId(UUID id) {
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

