package com.ideasync.ideasyncbackend.user;

import com.ideasync.ideasyncbackend.user.dto.RegisterRequest;
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


  @DeleteMapping("/deleteUser")
  public String deleteUser(@RequestParam String username) {
    return userService.deleteUser(username);
  }

  @PostMapping("/register")
  public String registerUser(@RequestBody RegisterRequest registerRequest) {
    return userService.registerUser(registerRequest);
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
