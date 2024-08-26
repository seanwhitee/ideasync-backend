package com.ideasync.ideasyncbackend.applicant;

import com.ideasync.ideasyncbackend.applicant.dto.ApplicantResponse;
import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/applicant")
public class ApplicantController {
    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @GetMapping("/getApplicants")
    public List<ApplicantResponse> getApplicants(@RequestParam UUID projectId) {
        return applicantService.getApplicants(projectId);
    }

    @PostMapping("/addApplicant")
    public String addApplicant(@RequestParam UUID projectId, @RequestParam UUID userId) {
        return applicantService.addApplicant(projectId, userId);
    }

    @DeleteMapping("/deleteApplicant")
    public String deleteApplicant(@RequestParam UUID projectId, @RequestParam UUID userId) {
        return applicantService.deleteApplicant(projectId, userId);
    }

    @PostMapping("/acceptApplicant")
    public ApplicantResponse acceptApplicant(@RequestParam UUID projectId, @RequestParam UUID userId) {
        return applicantService.acceptApplicant(projectId, userId);
    }

    @PostMapping("/rejectApplicant")
    public ApplicantResponse rejectApplicant(@RequestParam UUID projectId, @RequestParam UUID userId) {
        return applicantService.rejectApplicant(projectId, userId);
    }

    @GetMapping("/getProjectAppliedByUser")
    public List<ProjectResponse> getProjectAppliedByUser(@RequestParam UUID userId) {
        return applicantService.getProjectAppliedByUser(userId);
    }
}
