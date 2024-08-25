package com.ideasync.ideasyncbackend.applicant;

import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {
    List<Applicant> findByProjectId(UUID projectId);
    Applicant findByProjectAndUser(Project project, User user);
    List<Applicant> findApplicantsByProject(Project project);
    List<Applicant> findApplicantsByUser(User user);
    List<Applicant> findApplicantsByUserAndVerified(User user, int verified);
}
