package  com.ideasync.ideasyncbackend.comment;

import jakarta.persistence.*;

import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.user.User;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "comment")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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
  private Long parentId;

  // Constructors, getters, and setters

  public Long getId() {
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

  public Long getParentId() {
    return parentId;
  }

  public void setId(Long id) {
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

  public void setParentId(Long parentId) {
      this.parentId = parentId;
  }
}
