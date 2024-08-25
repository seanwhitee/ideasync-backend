package com.ideasync.ideasyncbackend.applicant;

import com.ideasync.ideasyncbackend.applicant.dto.ApplicantResponse;
import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.project.ProjectRepository;
import com.ideasync.ideasyncbackend.project.ProjectService;
import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import com.ideasync.ideasyncbackend.projectstatus.ProjectStatus;
import com.ideasync.ideasyncbackend.user.User;
import com.ideasync.ideasyncbackend.user.UserRepository;
import com.ideasync.ideasyncbackend.user.UserService;
import com.ideasync.ideasyncbackend.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ApplicantService {
    private static final Logger logger = LoggerFactory.getLogger(ApplicantService.class);
    private final ApplicantRepository applicantRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ProjectService projectService;

    @Autowired
    public ApplicantService(ApplicantRepository applicantRepository, UserRepository userRepository, ProjectRepository projectRepository, UserService userService, ProjectService projectService) {
        this.applicantRepository = applicantRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.projectService = projectService;
    }
    public List<Long> getApplicantIds(Long projectId) {
        List<Applicant> applicants =  applicantRepository.findByProjectId(projectId);
        List<Long> applicantUserIds = new ArrayList<>();
        for (Applicant applicant : applicants) {
            applicantUserIds.add(applicant.getUser().getId());
        }
        return applicantUserIds;
    }

    public List<ApplicantResponse> getApplicants(Long projectId) {
        Project p = projectRepository.findProjectById(projectId);
        ProjectStatus projectStatus = p.getProjectStatus();
        List<Applicant> applicants = applicantRepository.findByProjectId(projectId);
        List<ApplicantResponse> applicantResponses = new ArrayList<>();

        for (Applicant app: applicants) {
            UserResponse userRes = userService.getUserResponse(app.getUser());
            int applicantStatus = app.getVerified();
            if ((projectStatus.getStatus().equals("member_recruiting") && app.getUser().getUserRole().getRoleName().equals("creator")
            || (projectStatus.getStatus().equals("mentor_recruiting") && app.getUser().getUserRole().getRoleName().equals("mentor"))
            || (projectStatus.getStatus().equals("complete")))) {
                applicantResponses.add(new ApplicantResponse(userRes, applicantStatus));
            }
        }

        return  applicantResponses;
    }

    public String addApplicant(Long projectId, Long userId) {
        // check if user is already an applicant
        List<Long> applicantUserIds = getApplicantIds(projectId);
        if (applicantUserIds.contains(userId)) {
            return "User is already an applicant";
        }

        // add applicant
        User user = userRepository.findUserById(userId);
        Project project = projectRepository.findProjectById(projectId);
        Applicant applicant = new Applicant();

        if (!user.isAllowProjectApply()) {
            return "User is not allowed to apply";
        }

        // if user is the host of project, not allow to apply
        if (Objects.equals(project.getUser().getId(), userId)) {
            return "User is the host of the project";
        }

        applicant.setUser(user);
        applicant.setProject(project);

        try {
            applicantRepository.save(applicant);
            return "Applicant added successfully";
        } catch (Exception e) {
            logger.error("Error adding applicant", e);
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
        List<Long> applicantUserIds = getApplicantIds(projectId);
        if (!applicantUserIds.contains(userId)) {
            return "User is not an applicant";
        }

        // delete applicant
        Applicant applicant = applicantRepository.findByProjectAndUser(project, user);
        try {
            applicantRepository.delete(applicant);
            return "Applicant deleted successfully";
        } catch (Exception e) {
            logger.error("Error deleting applicant", e);
            return "Error deleting applicant";
        }

    }

    public ApplicantResponse rejectApplicant(Long projectId, Long userId) {
        Project p = projectRepository.findProjectById(projectId);
        User u = userRepository.findUserById(userId);
        Applicant targetRecord = applicantRepository
                .findByProjectAndUser(p, u);
        targetRecord.setVerified(-1);
        try {
            applicantRepository.save(targetRecord);
            return new ApplicantResponse(userService.getUserResponse(u), -1);
        } catch (Exception e) {
            logger.error("Error rejecting applicant", e);
            return null;
        }
    }

    public ApplicantResponse acceptApplicant(Long projectId, Long userId) {
        Project p = projectRepository.findProjectById(projectId);
        User u = userRepository.findUserById(userId);
        Applicant targetRecord = applicantRepository
                .findByProjectAndUser(p, u);
        targetRecord.setVerified(1);
        try {
            applicantRepository.save(targetRecord);
            return new ApplicantResponse(userService.getUserResponse(u), 1);
        } catch (Exception e) {
            logger.error("Error accepting applicant", e);
            return null;
        }
    }

    public List<ProjectResponse> getProjectAppliedByUser(Long userId) {
        User u = userRepository.findUserById(userId);
        List<Applicant> applicants = applicantRepository.findApplicantsByUser(u);
        
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for (Applicant app: applicants) {
            Project p = app.getProject();

            ProjectResponse ps = projectService.setProjectResponse(p);
            List<ApplicantResponse> applicantResponses = new ArrayList<>();
            for (Applicant appInP : p.getApplicants()) {
                applicantResponses.add(
                        new ApplicantResponse((
                                userService.getUserResponse(appInP.getUser())),
                                appInP.getVerified())
                );
            }

            ps.setApplicants(applicantResponses);
            projectResponses.add(ps);
        }
        return projectResponses;
    }
}
