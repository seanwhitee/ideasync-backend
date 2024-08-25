package  com.ideasync.ideasyncbackend.comment;

import jakarta.persistence.*;

import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "comment")
public class Comment {

  @Id @GeneratedValue
  @JdbcTypeCode(Types.VARCHAR)
  private UUID id;

  @CreationTimestamp
  @Column(name = "createAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp createAt;

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "projectId", nullable = false)
  private Project project;

  @Column(length = 100)
  private String text;

  @Column(name = "parentId")
  private UUID parentId;

  // Constructors, getters, and setters

  public UUID getId() {
    return id;
  }

  public Timestamp getCreateAt() {
    return createAt;
  }

  public User getUser() {
    return user;
  }

  public Project getProject() {
    return project;
  }

  public String getText() {
    return text;
  }

  public UUID getParentId() {
    return parentId;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setCreateAt(Timestamp createAt) {
    this.createAt = createAt;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setParentId(UUID parentId) {
      this.parentId = parentId;
  }
}
