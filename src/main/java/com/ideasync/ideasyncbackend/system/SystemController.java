package com.ideasync.ideasyncbackend.system;

import com.ideasync.ideasyncbackend.user.dto.RegisterRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/system")
public class SystemController {
  private final SystemService systemService;

  public SystemController(SystemService systemService) {
    this.systemService = systemService;
  }

  @PostMapping("/systemInit")
  public boolean systemInit(@RequestBody RegisterRequest adminUser) {
    return systemService.systemInit(adminUser);
  }
}
