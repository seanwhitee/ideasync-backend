package com.ideasync.ideasyncbackend.archive;

import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/archive")
public class ArchiveController {
    private  final ArchiveService archiveService;

    public ArchiveController(ArchiveService archiveService){
        this.archiveService = archiveService;
    }

    @GetMapping("/getArchives")
    public List<ProjectResponse> getArchives(@RequestParam Long userId) {
        return archiveService.getArchives(userId);
    }

    @PostMapping("/addArchive")
    public String addArchive(@RequestParam Long projectId, @RequestParam Long userId) {
        return archiveService.addArchive(projectId, userId);
    }

    @DeleteMapping("/deleteArchive")
    public String deleteArchive(@RequestParam Long projectId, @RequestParam Long userId) {
        return archiveService.deleteArchive(projectId, userId);
    }
}
