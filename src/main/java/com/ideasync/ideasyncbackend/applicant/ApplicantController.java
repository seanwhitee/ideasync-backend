package com.ideasync.ideasyncbackend.applicant;

import com.ideasync.ideasyncbackend.applicant.dto.ApplicantResponse;
import com.ideasync.ideasyncbackend.project.dto.ProjectResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/applicant")
public class ApplicantController {
    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @GetMapping("/getApplicants")
    public List<ApplicantResponse> getApplicants(@RequestParam Long projectId) {
        return applicantService.getApplicants(projectId);
    }

    @PostMapping("/addApplicant")
    public String addApplicant(@RequestParam Long projectId, @RequestParam Long userId) {
        return applicantService.addApplicant(projectId, userId);
    }

    @DeleteMapping("/deleteApplicant")
    public String deleteApplicant(@RequestParam Long projectId, @RequestParam Long userId) {
        return applicantService.deleteApplicant(projectId, userId);
    }

    @PostMapping("/acceptApplicant")
    public ApplicantResponse acceptApplicant(@RequestParam Long projectId, @RequestParam Long userId) {
        return applicantService.acceptApplicant(projectId, userId);
    }

    @PostMapping("/rejectApplicant")
    public ApplicantResponse rejectApplicant(@RequestParam Long projectId, @RequestParam Long userId) {
        return applicantService.rejectApplicant(projectId, userId);
    }

    @GetMapping("/getProjectAppliedByUser")
    public List<ProjectResponse> getProjectAppliedByUser(@RequestParam Long userId) {
        return applicantService.getProjectAppliedByUser(userId);
    }
}
