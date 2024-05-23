package com.ideasync.ideasyncbackend.archive;

import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.project.ProjectRepository;
import com.ideasync.ideasyncbackend.user.User;
import com.ideasync.ideasyncbackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ArchiveService {
    private final ArchiveRepository archiveRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public ArchiveService(ArchiveRepository archiveRepository,
                          ProjectRepository projectRepository,
                          UserRepository userRepository) {
        this.archiveRepository = archiveRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }
    public String deleteArchive(Long projectId, Long userId) {
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
            System.out.println(e);
            return "Error deleting archive";
        }
    }
    public List<Long> getArchives(Long userId) {
        List<Archive> archives = archiveRepository.findArchivesByUserId(userId);
        List<Long> archiveProjectsIds = new ArrayList<>();
        for (Archive archive : archives) {
            archiveProjectsIds.add(archive.getProject().getId());
        }
        return archiveProjectsIds;
    }
    public String addArchive(Long projectId, Long userId) {
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
            System.out.println(e);
            return "Error adding archive";

        }

    }
}
