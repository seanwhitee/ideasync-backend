package com.ideasync.ideasyncbackend.project;


import com.ideasync.ideasyncbackend.applicant.Applicant;
import com.ideasync.ideasyncbackend.applicant.ApplicantRepository;
import com.ideasync.ideasyncbackend.applicant.ApplicantService;
import com.ideasync.ideasyncbackend.archive.ArchiveRepository;
import com.ideasync.ideasyncbackend.comment.CommentRepository;
import com.ideasync.ideasyncbackend.comment.CommentService;
import com.ideasync.ideasyncbackend.project.dto.ProjectRequest;
import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import com.ideasync.ideasyncbackend.projectimage.ProjectImage;
import com.ideasync.ideasyncbackend.projectimage.ProjectImageRepository;
import com.ideasync.ideasyncbackend.projectstatus.ProjectStatus;
import com.ideasync.ideasyncbackend.projectstatus.ProjectStatusRepository;
import com.ideasync.ideasyncbackend.projectstatus.ProjectStatusService;
import com.ideasync.ideasyncbackend.tag.Tag;
import com.ideasync.ideasyncbackend.tag.TagRepository;
import com.ideasync.ideasyncbackend.user.User;
import com.ideasync.ideasyncbackend.user.UserRepository;
import com.ideasync.ideasyncbackend.user.UserService;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Service
public class ProjectService {
    private final VectorStore vectorStore;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final ProjectImageRepository projectImageRepository;
    private final ArchiveRepository archiveRepository;
    private final ApplicantRepository applicantRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ApplicantService applicantService;
    private final CommentService commentService;
    private final ProjectStatusService projectStatusService;
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
    private final ProjectStatusRepository projectStatusRepository;


    @Autowired
    public ProjectService(VectorStore vectorStore,
                          ProjectRepository projectRepository,
                          UserRepository userRepository,
                          TagRepository tagRepository,
                          ProjectImageRepository projectImageRepository,
                          ArchiveRepository archiveRepository,
                          ApplicantRepository applicantRepository,
                          CommentRepository commentRepository,
                          UserService userService,
                          @Lazy ApplicantService applicantService,
                          CommentService commentService,
                          @Lazy ProjectStatusService projectStatusService, ProjectStatusRepository projectStatusRepository) {

        this.vectorStore = vectorStore;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.projectImageRepository = projectImageRepository;
        this.archiveRepository = archiveRepository;
        this.applicantRepository = applicantRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.applicantService = applicantService;
        this.commentService = commentService;
        this.projectStatusService = projectStatusService;
        this.projectStatusRepository = projectStatusRepository;
    }

    public boolean isValidProjectData(ProjectRequest projectRequest) {
        String title = projectRequest.getTitle();
        String description = projectRequest.getDescription();
        String statusId = projectRequest.getStatus();
        String requireSkills = projectRequest.getRequireSkills();
        String school = projectRequest.getSchool();
        int allowApplicantsNum = projectRequest.getAllowApplicantsNum();

        // if all data is valid
        // and the user is allowed to create project
        // and the user did not create the project with the same title
        return (title != null && !title.isEmpty())
                && (description != null && !description.isEmpty())
                && (statusId != null)
                && (school != null && !school.isEmpty())
                && (requireSkills != null && !requireSkills.isEmpty())
                && (allowApplicantsNum != 0);
    }

    public List<ProjectResponse> getProjectsByUser(UUID userId) {
        List<Project> projects = projectRepository.findProjectsByUser(userRepository.findUserById(userId));
        List<ProjectResponse> responses = new ArrayList<>();
        for (Project project : projects) {
            responses.add(setProjectResponse(project));
        }
        return responses;
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
                userService.getUserResponse(project.getUser()),
                project.getProjectStatus().getStatus(),
                project.getTitle(),
                project.getDescription(),
                project.getSchool(),
                project.getAllowApplicantsNum(),
                project.isGraduationProject(),
                project.getCreateAt(),
                images,
                tagNames,
                project.getRequireSkills(),
                applicantService.getApplicants(project.getId()),
                commentService.getAllCommentChunks(project.getId())
        );

    }

    public String createProject(ProjectRequest projectRequest) {
        // get user who intends to create the project
        Optional<User> user = userRepository.findById(projectRequest.getHostId());
        Project project = new Project();


        // check if user is present
        if (user.isEmpty()) {
            return "User not found";
        }

        User currentUser = user.get();

        // check if user is allowed to create project
        if (!currentUser.isAllowProjectCreate()) {
            return "User is not allowed to create project";
        }

        // check if project data is valid
        if (!isValidProjectData(projectRequest)) {
            return "Invalid project data";
        }

        // check if project with the same title exists
        if (!checkProjectWithoutSameTitle(currentUser, projectRequest.getTitle())) {
            return "Project with the same title already exists";
        }

        // set project data
        project.setUser(user.get());
        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());
        project.setProjectStatus(projectStatusRepository.findByStatus(projectRequest.getStatus()));
        project.setGraduationProject(projectRequest.getGraduationProject());
        project.setSchool(projectRequest.getSchool());
        project.setAllowApplicantsNum(projectRequest.getAllowApplicantsNum());
        project.setRequireSkills(projectRequest.getRequireSkills());

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

            // save tags to vector store
            List<Document> documents = new ArrayList<>();
            for (Tag tag : tagRepository.findTagsByProject(project)) {
                Map<String, Object> metadata = new HashMap<>();
                metadata.put("tag_id", tag.getId());
                metadata.put("project_id", project.getId());
                documents.add(new Document(tag.getName(), metadata));
            }

            vectorStore.add(documents);


            return "Project created successfully";

        } catch (Exception e) {
            return "Project created failed";
        }
    }

    public List<ProjectResponse> searchProject(String searchString) {
        List<ProjectResponse> result = new ArrayList<>();

        // search for title
        List<Project> projects = projectRepository.findProjectsByTitleContaining(searchString);

        for (Project project : projects) {
            result.add(setProjectResponse(project));
        }
        return result;
    }


    /** TODO: do not forget to delete the image at s3 from frontend.
     * Method to delete the project
     *
     * @param id project id
     * @return success message
     */
    public String deleteProjectById(UUID id) {

        Optional<Project> project = projectRepository.findById(id);

        if (project.isPresent()) {
            List<Tag> tags = tagRepository.findTagsByProject(project.get());
            try {
                // Delete tags from vector store which project_id metadata is equal to project id.
                FilterExpressionBuilder b = new FilterExpressionBuilder();

                List<Document> results = vectorStore.similaritySearch(SearchRequest.defaults()
                                .withTopK(tags.size())
                                .withFilterExpression(b.eq("project_id", id).build()));

                List<String> idList = new ArrayList<>();
                for (Document document : results) {
                    idList.add(document.getId());
                }
                vectorStore.delete(idList);
            } catch (Exception e) {
                return "Project deletion failed";
            }

            try {
                tagRepository.deleteAll(tags);
                projectImageRepository.deleteAll(projectImageRepository.findProjectImagesByProject(project.get()));
                archiveRepository.deleteAll(archiveRepository.findArchivesByProject(project.get()));
                applicantRepository.deleteAll(applicantRepository.findApplicantsByProject(project.get()));
                commentRepository.deleteAll(commentRepository.findCommentsByProject(project.get()));
                projectRepository.delete(project.get());

                return "Project deleted successfully";
            } catch (Exception e) {
                return "Project deletion failed";
            }
        }
        return "Project not found";
    }

    public ProjectResponse getProjectById(UUID id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(this::setProjectResponse).orElse(null);
    }

    public List<ProjectResponse> getRelatedProjects(UUID projectId) {
        /* Prepare feature string to get recommendation */
        StringBuilder s = new StringBuilder();
        Project p = projectRepository.findProjectById(projectId);
        for (Tag t: p.getTags()) {
            s.append(t.getName());
        }
        s.append(p.getDescription());
        List<UUID> relatedProjectIds = projectStatusService.getRecommendProjectIds(s.toString());
        // filter out the one with id equals to projectId
        relatedProjectIds.remove(projectId);
        List<ProjectResponse> responses = new ArrayList<>();
        for (UUID id: relatedProjectIds) {
            responses.add(getProjectById(id));
        }
        return responses;
    }

    public ProjectResponse changeProjectStatus(UUID projectId, String changeToWhat, String nextOrPrevious) {
        Project project = projectRepository.findProjectById(projectId);
        if (nextOrPrevious.equals("next")) {
            // check if applicants all reviewed
            List<Applicant> applicants = applicantRepository.findApplicantsByProject(project);
            for (Applicant app: applicants) {
                if (app.getVerified() == 0) {
                    return null;
                }
            }

            int acceptedApplicantsNum = 0;
            for (Applicant app: applicants) {
                if (app.getVerified() == 1) {
                    acceptedApplicantsNum++;
                }
            }
            // check if allowApplicantsNum > acceptedApplicantsNum
            int allowApplicantsNum = project.getAllowApplicantsNum();

            if (allowApplicantsNum < acceptedApplicantsNum) {
                return null;
            }
        }
        try {
            ProjectStatus statusChangeTo = projectStatusRepository.findByStatus(changeToWhat);
            project.setProjectStatus(statusChangeTo);
            projectRepository.save(project);
        } catch (Exception e) {
            logger.error("Error changing project status", e);
            return null;
        }
        return setProjectResponse(project);
    }

}