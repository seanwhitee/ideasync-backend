package com.ideasync.ideasyncbackend.user;

import com.ideasync.ideasyncbackend.user.dto.LoginResponse;
import com.ideasync.ideasyncbackend.user.dto.RegisterRequest;
import com.ideasync.ideasyncbackend.user.dto.UserResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
  private final UserService userService;
  // get env
  Map<String, String> env = System.getenv();

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/sendEmail")
  public String sendEmail(@RequestParam String email, @RequestParam int passCode){
    return userService.sendPassCodeEmail(env.get("COMPANY_EMAIL"), email, passCode);
  }
  @DeleteMapping("/deleteUser")
  public String deleteUser(@RequestParam UUID id) {
    return userService.deleteUser(id);
  }

  @PostMapping("/register")
  public String registerUser(@RequestBody User user) {
    return userService.registerUser(user);
  }

  @PostMapping("/saveUserData")
  public String saveUserData(@RequestBody RegisterRequest userRequest) {

    return userService.saveUserData(userRequest);
  }

  @GetMapping("/getDetail")
  public UserResponse getUser(@RequestParam UUID id) {
    return userService.getUser(id);
  }

  @GetMapping("/login")
  public LoginResponse userLogin(@RequestParam String username, @RequestParam String password) throws Exception {
    return userService.userLogin(username, password);
  }

  @PatchMapping("/updateRoleStatus")
  public String updateRoleStatus(@RequestParam UUID id, @RequestParam boolean status) {
    return userService.updateRoleStatus(id, status);
  }

  @GetMapping("/getAllUsers")
  public List<UserResponse> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/countUserComments")
  public int countUserComments(@RequestParam UUID userId) {
    return  userService.countUserComments(userId);
  }

  @GetMapping("/countAccept")
  public int countAccept(@RequestParam UUID userId) {
    return userService.countAccept(userId);
  }
}
