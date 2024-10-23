package com.ideasync.ideasyncbackend.system;

import com.ideasync.ideasyncbackend.projectstatus.ProjectStatus;
import com.ideasync.ideasyncbackend.projectstatus.ProjectStatusRepository;
import com.ideasync.ideasyncbackend.user.UserService;
import com.ideasync.ideasyncbackend.user.dto.RegisterRequest;
import com.ideasync.ideasyncbackend.userrole.UserRole;
import com.ideasync.ideasyncbackend.userrole.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SystemService {


  private final ProjectStatusRepository projectStatusRepository;
  private static final Logger logger = LoggerFactory.getLogger(SystemService.class);
  private final UserRoleRepository userRoleRepository;
  private final UserService userService;

  @Autowired
  public SystemService(ProjectStatusRepository projectStatusRepository, UserRoleRepository userRoleRepository, UserService userService) {

    this.projectStatusRepository = projectStatusRepository;
    this.userRoleRepository = userRoleRepository;
    this.userService = userService;
  }

  private void initUserRole() {
    List<String> roles = new ArrayList<>(Arrays.asList("creator", "mentor", "admin"));
    for (String role : roles) {
        UserRole userRole = new UserRole();
        try {
          userRole.setRoleName(role);
          userRoleRepository.save(userRole);
        } catch (Exception e) {
          logger.info("Failed to save user role: " + role);
          logger.error(e.getMessage());
        }
    }
  }

  private void initProjectStatus() {
    List<String> statuses = new ArrayList<>(Arrays.asList("member_recruiting", "mentor_recruiting", "complete"));
    for (String status : statuses) {
        ProjectStatus projectStatus = new ProjectStatus();
        try {
          projectStatus.setStatus(status);
          projectStatusRepository.save(projectStatus);
        } catch (Exception e) {
          logger.info("Failed to save project status: " + status);
          logger.error(e.getMessage());
        }
    }
  }

  private void createAdminUser(RegisterRequest adminUser) {
    userService.saveUserData(adminUser);
  }

  public boolean systemInit(RegisterRequest adminUser) {
    initProjectStatus();
    initUserRole();
    createAdminUser(adminUser);

    return true;
  }
}
