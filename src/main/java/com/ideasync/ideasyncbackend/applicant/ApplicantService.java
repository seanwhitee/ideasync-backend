package com.ideasync.ideasyncbackend.applicant;

import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.project.ProjectRepository;
import com.ideasync.ideasyncbackend.user.User;
import com.ideasync.ideasyncbackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public ApplicantService(ApplicantRepository applicantRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.applicantRepository = applicantRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }
    public List<Long> getApplicants(Long projectId) {
        List<Applicant> applicants =  applicantRepository.findByProjectId(projectId);
        List<Long> applicantUserIds = new ArrayList<>();
        for (Applicant applicant : applicants) {
            applicantUserIds.add(applicant.getUser().getId());
        }
        return applicantUserIds;
    }

    public String addApplicant(Long projectId, Long userId) {
        // check if user is already an applicant
        List<Long> applicantUserIds = getApplicants(projectId);
        if (applicantUserIds.contains(userId)) {
            return "User is already an applicant";
        }

        // add applicant
        User user = userRepository.findUserById(userId);
        Project project = projectRepository.findProjectById(projectId);
        Applicant applicant = new Applicant();

        // if user is the host of project, not allow to apply
        if (Objects.equals(project.getUser().getId(), userId)) {
            return "User is the host of the project";
        }

        applicant.setUser(user);
        applicant.setProject(project);

        try {
            applicantRepository.save(applicant);

            if(user.getUserRole().getId() == 1) {
                project.setApplicantCount(project.getApplicantCount() + 1);
            }
            projectRepository.save(project);
            return "Applicant added successfully";
        } catch (Exception e) {
            System.out.println(e);
            return "Error adding applicant";
        }
    }

    public String deleteApplicant(Long projectId, Long userId) {
        // check user and project exist
        User user = userRepository.findUserById(userId);
        Project project = projectRepository.findProjectById(projectId);

        if (user == null) {
            return "User not found";
        }

        if (project == null) {
            return "Project not found";
        }

        // check if user is an applicant
        List<Long> applicantUserIds = getApplicants(projectId);
        if (!applicantUserIds.contains(userId)) {
            return "User is not an applicant";
        }

        // delete applicant
        Applicant applicant = applicantRepository.findByProjectAndUser(project, user);
        try {
            applicantRepository.delete(applicant);

            if(user.getUserRole().getId() == 1) {
                project.setApplicantCount(project.getApplicantCount() - 1);
            }

            projectRepository.save(project);
            return "Applicant deleted successfully";
        } catch (Exception e) {
            System.out.println(e);
            return "Error deleting applicant";
        }

    }
}
