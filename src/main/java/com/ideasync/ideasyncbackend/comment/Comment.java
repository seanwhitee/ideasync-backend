package  com.ideasync.ideasyncbackend.comment;

import jakarta.persistence.*;

import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.user.User;

@Entity
@Table(name = "comment")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "projectId", nullable = false)
  private Project project;

  @Column(length = 100)
  private String text;

  // Constructors, getters, and setters

  public Long getId() {
    return id;
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

  public void setId(Long id) {
    this.id = id;
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
}
