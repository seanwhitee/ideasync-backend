package  com.ideasync.ideasyncbackend.applicant;

import jakarta.persistence.*;

import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.user.User;

@Entity
@Table(name = "applicant")
public class Applicant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "projectId", nullable = false)
  private Project project;

  // Constructors, getters, and setters
}
