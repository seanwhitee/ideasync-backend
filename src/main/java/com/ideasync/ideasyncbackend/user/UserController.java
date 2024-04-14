package com.ideasync.ideasyncbackend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
  private final UserService userService;
  public UserController(UserService userService) {
    this.userService = userService;
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
