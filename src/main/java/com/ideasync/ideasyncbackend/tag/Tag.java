package com.ideasync.ideasyncbackend.tag;
import jakarta.persistence.*;

import com.ideasync.ideasyncbackend.project.Project;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "tag")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @CreationTimestamp
  @Column(name = "createAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp createAt;

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

  public Timestamp getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Timestamp createAt) {
    this.createAt = createAt;
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
