package com.ideasync.ideasyncbackend.project;


import com.ideasync.ideasyncbackend.project.dto.ProjectRequest;
import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import com.ideasync.ideasyncbackend.projectimage.ProjectImage;
import com.ideasync.ideasyncbackend.projectimage.ProjectImageRepository;
import com.ideasync.ideasyncbackend.projectstatus.ProjectStatus;
import com.ideasync.ideasyncbackend.tag.Tag;
import com.ideasync.ideasyncbackend.tag.TagRepository;
import com.ideasync.ideasyncbackend.user.User;
import com.ideasync.ideasyncbackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final ProjectImageRepository projectImageRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, TagRepository tagRepository, ProjectImageRepository projectImageRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.projectImageRepository = projectImageRepository;
    }

    public boolean isValidProjectData(ProjectRequest projectRequest) {
        String title = projectRequest.getTitle();
        String description = projectRequest.getDescription();
        Long statusId = projectRequest.getStatusId();
        String school = projectRequest.getSchool();
        int allowApplicantsNum = projectRequest.getAllowApplicantsNum();
        int applicantCount = projectRequest.getApplicantCount();

        // if all data is valid
        // and the user is allowed to create project
        // and the user did not create the project with the same title
        return (title != null && !title.isEmpty())
                && (description != null && !description.isEmpty())
                && (statusId != null)
                && (school != null && !school.isEmpty())
                && (allowApplicantsNum != 0)
                && (applicantCount <= 0);
    }

    public boolean checkProjectWithoutSameTitle(User user, String title) {
        List<Project> projects = projectRepository.findProjectsByUser(user);
        for (Project project : projects) {
            if (project.getTitle().equals(title)) {
                return false;
            }
        }
        return true;
    }

    public ProjectResponse setProjectResponse(Project project) {

        // get list of string contain the image URLs
        List<ProjectImage> projectImages = projectImageRepository.findProjectImagesByProject(project);
        List<String> images = new ArrayList<>();
        for (ProjectImage projectImage : projectImages) {
            images.add(projectImage.getImageUrl());
        }

        // get list of string contain the tags
        List<Tag> tags = tagRepository.findTagsByProject(project);
        List<String> tagNames = new ArrayList<>();
        for (Tag tag : tags) {
            tagNames.add(tag.getName());
        }

        return new ProjectResponse(
                project.getId(),
                project.getUser().getId(),
                project.getProjectStatus().getId(),
                project.getTitle(),
                project.getDescription(),
                project.getSchool(),
                project.getAllowApplicantsNum(),
                project.getApplicantCount(),
                project.isGraduationProject(),
                project.getCreateAt(),
                images,
                tagNames

        );

    }

    public List<ProjectResponse> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for (Project project : projects) {
            projectResponses.add(setProjectResponse(project));
        }
        return projectResponses;
    }

    public String createProject(ProjectRequest projectRequest) {
        // get user who intends to create the project
        Optional<User> user = userRepository.findById(projectRequest.getHostId());
        Project project = new Project();


        // check user is allowed to create project
        if (user.isPresent()) {
            if (!user.get().isAllowProjectCreate()) {
                return "User is not allowed to create project";
            } else if (!isValidProjectData(projectRequest)) {
                return "Invalid project data";
            } else if (!checkProjectWithoutSameTitle(user.get(), projectRequest.getTitle())) {
                return "Project with the same title already exists";
            }
        } else {
            return "User not found";
        }

        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setId(projectRequest.getStatusId());

        // set project data
        project.setUser(user.get());
        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());
        project.setProjectStatus(projectStatus);
        project.setGraduationProject(projectRequest.getGraduationProject());
        project.setSchool(projectRequest.getSchool());
        project.setAllowApplicantsNum(projectRequest.getAllowApplicantsNum());
        project.setApplicantCount(projectRequest.getApplicantCount());

        try {
            projectRepository.save(project);

            // save project tags and images to the database
            List<String> tags = projectRequest.getTags();
            List<String> images = projectRequest.getProjectImages();
            if (tags != null && !tags.isEmpty()) {
                for (String tag : tags) {
                    Tag newTag = new Tag();
                    newTag.setName(tag);
                    newTag.setProject(project);
                    tagRepository.save(newTag);
                }
            }
            if (images != null && !images.isEmpty()) {
                for (String image : projectRequest.getProjectImages()) {
                    ProjectImage newImage = new ProjectImage();
                    newImage.setImageUrl(image);
                    newImage.setProject(project);
                    projectImageRepository.save(newImage);
                }
            }

            userRepository.save(user.get());
            return "Project created successfully";

        } catch (Exception e) {
            return "Project created failed";
        }
    }

    /** TODO: do not forget to delete the image at s3 from frontend.
     * Method to delete the project
     *
     * @param id project id
     * @return success message
     */
    public String deleteProjectById(Long id) {

        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            try {
                projectRepository.delete(project.get());
                return "Project deleted successfully";
            } catch (Exception e) {
                return "Project deletion failed";
            }
        }
        return "Project not found";
    }
}