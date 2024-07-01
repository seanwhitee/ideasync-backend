package com.ideasync.ideasyncbackend.applicant;

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

    @GetMapping("/getApplicantIds")
    public List<Long> getApplicants(@RequestParam Long projectId) {
        return applicantService.getApplicantIds(projectId);
    }

    @PostMapping("/addApplicant")
    public String addApplicant(@RequestParam Long projectId, @RequestParam Long userId) {
        return applicantService.addApplicant(projectId, userId);
    }

    @DeleteMapping("/deleteApplicant")
    public String deleteApplicant(@RequestParam Long projectId, @RequestParam Long userId) {
        return applicantService.deleteApplicant(projectId, userId);
    }
}
