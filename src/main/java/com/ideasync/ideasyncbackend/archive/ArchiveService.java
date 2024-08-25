package com.ideasync.ideasyncbackend.archive;

import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.project.ProjectRepository;
import com.ideasync.ideasyncbackend.project.ProjectService;
import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import com.ideasync.ideasyncbackend.user.User;
import com.ideasync.ideasyncbackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ArchiveService {
    private static final Logger logger = LoggerFactory.getLogger(ArchiveService.class);
    private final ArchiveRepository archiveRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectService projectService;

    @Autowired
    public ArchiveService(ArchiveRepository archiveRepository,
                          ProjectRepository projectRepository,
                          UserRepository userRepository, ProjectService projectService) {
        this.archiveRepository = archiveRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectService = projectService;
    }
    public String deleteArchive(UUID projectId, UUID userId) {
        Project project = projectRepository.findProjectById(projectId);
        if (project == null) {
            return "Project not found";
        }
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return "User not found";
        }
        Archive archive = archiveRepository.findArchiveByProjectAndUser(project, user);
        if (archive == null) {
            return "Archive not found";
        }
        try {
            archiveRepository.delete(archive);
            return "Archive deleted successfully";
        } catch (Exception e) {
            logger.error("Error deleting archive", e);
            return "Error deleting archive";
        }
    }
    public List<ProjectResponse> getArchives(UUID userId) {
        List<Archive> archives = archiveRepository.findArchivesByUserId(userId);
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for (Archive archive : archives) {
            Project project = archive.getProject();
            if (!project.getProjectStatus().getStatus().equals("complete")) {
                projectResponses.add(projectService.setProjectResponse(project));
            }
        }

        return projectResponses;

    }
    public String addArchive(UUID projectId, UUID userId) {
        // prepare user and project
        Project project = projectRepository.findProjectById(projectId);
        if (project == null) {
            return "Project not found";
        }
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return "User not found";
        }

        // check if the project is already archived
        Archive existingArchive = archiveRepository.findArchiveByProjectAndUser(project, user);
        if (existingArchive != null) {
            return "Project already archived";
        }

        try {
            Archive archive = new Archive();
            archive.setProject(project);
            archive.setUser(user);
            archiveRepository.save(archive);
            return "Archive added successfully";
        } catch (Exception e) {
            logger.error("Error adding archive", e);
            return "Error adding archive";

        }

    }
}
