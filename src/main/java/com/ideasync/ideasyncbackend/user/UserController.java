package com.ideasync.ideasyncbackend.user;

import com.ideasync.ideasyncbackend.user.dto.UserResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
  public String deleteUser(@RequestParam Long id) {
    return userService.deleteUser(id);
  }

  @PostMapping("/register")
  public String registerUser(@RequestBody User user) {
    return userService.registerUser(user);
  }

  @PostMapping("/saveUserData")
  public String saveUserData(@RequestBody User user) {
    return userService.saveUserData(user);
  }

  @GetMapping("/{id}")
  public UserResponse getUser(@PathVariable Long id) {
    return userService.getUser(id);
  }

  @GetMapping("/login")
  public UserResponse userLogin(@RequestParam String username, @RequestParam String password) {
    return userService.userLogin(username, password);
  }

  @PatchMapping("/updateRoleStatus")
  public String updateRoleStatus(@RequestParam Long id, @RequestParam boolean status) {
    return userService.updateRoleStatus(id, status);
  }

  @GetMapping("/getAllUsers")
  public List<UserResponse> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("getToken")
  public String getToken(@RequestParam String username, @RequestParam String password){
    return userService.getToken(username,password);
  }

  @GetMapping("verifyAuthority")
  public String verifyAuthority(){
    return userService.verifyAuthority();
  }
}
