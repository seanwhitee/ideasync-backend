package com.ideasync.ideasyncbackend.projectstatus;

import com.ideasync.ideasyncbackend.project.ProjectRepository;
import com.ideasync.ideasyncbackend.project.ProjectService;
import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import com.ideasync.ideasyncbackend.user.User;
import com.ideasync.ideasyncbackend.user.UserRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.PineconeVectorStore;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ideasync.ideasyncbackend.project.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ProjectStatusService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectStatusService.class);
    private final ProjectStatusRepository projectStatusRepository;
    private final ProjectService projectService;
    private final PineconeVectorStore vectorStore;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectStatusService(ProjectStatusRepository projectStatusRepository,
                                ProjectService projectService,
                                PineconeVectorStore vectorStore,
                                UserRepository userRepository,
                                ProjectRepository projectRepository) {
        this.projectStatusRepository = projectStatusRepository;
        this.projectService = projectService;
        this.vectorStore = vectorStore;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public List<ProjectResponse> getRecommendProjectsByStatus(String status, long userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            logger.info("User not found");
            return new ArrayList<>();
        }

        List<Long> recommendProjectIds = getRecommendProjectIds(
                user.getProfileDescription());
        List<Project> projects = getProjectsByStatus(status);
        List<Project> sortedProjects = sortProjects(recommendProjectIds, projects);
        return convertToProjectResponses(sortedProjects);
    }

    public List<Long> getRecommendProjectIds(String s) {
        List<Document> recommendProjects = vectorStore.similaritySearch(SearchRequest.defaults()
                .withQuery(s)
                .withTopK(10));

        List<Long> recommendProjectIds = new ArrayList<>();
        for (Document document : recommendProjects) {
            Map<String, Object> metaData =  document.getMetadata();
            Double projectIdDouble = (Double) metaData.get("project_id");
            Long projectId = projectIdDouble.longValue();

            if (!recommendProjectIds.contains(projectId)) {
                recommendProjectIds.add(projectId);
            }
        }
        return recommendProjectIds;
    }

    private List<Project> getProjectsByStatus(String status) {
        if (Objects.equals(status, "member_recruiting")) {
            return projectRepository.findProjectByProjectStatus(
                    projectStatusRepository.findStatusById(1)
            );
        } else if (Objects.equals(status, "mentor_recruiting")) {
            return projectRepository.findProjectByProjectStatus(
                    projectStatusRepository.findStatusById(2)
            );
        }
        return new ArrayList<>();
    }

    private List<Project> sortProjects(List<Long> recommendProjectIds, List<Project> projects) {
        List<Project> sortedProjects = new ArrayList<>();
        for (Long projectId : recommendProjectIds) {
            for (Project project : projects) {
                if (Objects.equals(project.getId(), projectId)) {
                    sortedProjects.add(project);
                }
            }
        }

        /* Attach the rest of the projects. */
        for (Project project : projects) {
            if (!sortedProjects.contains(project)) {
                sortedProjects.add(project);
            }
        }
        return sortedProjects;
    }

    private List<ProjectResponse> convertToProjectResponses(List<Project> sortedProjects) {
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for (Project project : sortedProjects) {
            ProjectResponse projectResponse = projectService.setProjectResponse(project);
            projectResponses.add(projectResponse);
        }
        return projectResponses;
    }
}
