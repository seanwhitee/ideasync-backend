package com.ideasync.ideasyncbackend.user;

import com.ideasync.ideasyncbackend.user.dto.PassCodeResponse;
import com.ideasync.ideasyncbackend.user.dto.UserResponse;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/generatePassCode")
  public PassCodeResponse generatePassCode(@RequestParam String username, @RequestParam String email) {
    return userService.generatePassCode(username, email);
  }

  @DeleteMapping("/deleteUser")
  public String deleteUser(@RequestParam String username) {
    return userService.deleteUser(username);
  }

  @PatchMapping("/markEmailAsVerified")
  public String markEmailAsVerified(@RequestParam String username) {
    return userService.markEmailAsVerified(username);
  }
  @PostMapping("/register")
  public String registerUser(@RequestBody User user) {
    return userService.registerUser(user);
  }

  @GetMapping("/{id}")
  public UserResponse getUser(@PathVariable Long id) {
    return userService.getUser(id);
  }

  @GetMapping("/login")
  public UserResponse userLogin(@RequestParam String username, @RequestParam String password) {
    return userService.userLogin(username, password);
  }
}
