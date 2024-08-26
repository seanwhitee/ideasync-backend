package com.ideasync.ideasyncbackend.tag;
import jakarta.persistence.*;

import com.ideasync.ideasyncbackend.project.Project;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "tag")
public class Tag {

  @Id @GeneratedValue
  @JdbcTypeCode(Types.VARCHAR)
  private UUID id;

  @CreationTimestamp
  @Column(name = "createAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp createAt;

  @ManyToOne
  @JoinColumn(name = "projectId", nullable = false)
  private Project project;

  @Column(nullable = false)
  private String name;

  // Constructors, getters, and setters

  public UUID getId() {
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

  public void setId(UUID id) {
    this.id = id;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setName(String name) {
    this.name = name;
  }
}
