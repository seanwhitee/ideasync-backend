package com.ideasync.ideasyncbackend.archive;

import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/archive")
public class ArchiveController {
    private  final ArchiveService archiveService;

    public ArchiveController(ArchiveService archiveService){
        this.archiveService = archiveService;
    }

    @GetMapping("/getArchives")
    public List<ProjectResponse> getArchives(@RequestParam UUID userId) {
        return archiveService.getArchives(userId);
    }

    @PostMapping("/addArchive")
    public String addArchive(@RequestParam UUID projectId, @RequestParam UUID userId) {
        return archiveService.addArchive(projectId, userId);
    }

    @DeleteMapping("/deleteArchive")
    public String deleteArchive(@RequestParam UUID projectId, @RequestParam UUID userId) {
        return archiveService.deleteArchive(projectId, userId);
    }
}
