package  com.ideasync.ideasyncbackend.applicant;

import jakarta.persistence.*;

import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.user.User;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "applicant")
public class Applicant {

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

  @Column(name="verified", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
  private int verified;

  // Constructors, getters, and setters

  public int getVerified() {
    return verified;
  }

  public void setVerified(int verified) {
    this.verified = verified;
  }

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
}
