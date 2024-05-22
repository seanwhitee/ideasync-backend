package com.ideasync.ideasyncbackend.user;


import com.ideasync.ideasyncbackend.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service class for user operations.
 */
@Service
public class UserService {

  private final UserRepository userRepository;
  private final JavaMailSender emailSender;

  @Autowired
  public UserService(UserRepository userRepository, JavaMailSender emailSender) {
    this.userRepository = userRepository;
    this.emailSender = emailSender;
  }

  /**
   * Method to login user.
   *
   * @param username username of user
   * @param password password of user
   * @return UserResponse
   */
  public UserResponse userLogin(String username, String password) {
    User userData = userRepository.findByUserName(username);

    // check if user exist, and user role is verified
    if (userData == null || !userData.isRoleVerified()) {
      return null;
    }

    // check if password is correct
    if (userData.getPassword().equals(password)) {
      return getUserResponse(userData);
    }

    // return null if password is incorrect
    return null;
  }

  private UserResponse getUserResponse(User userData) {
    UserResponse response = new UserResponse();
    response.setId(userData.getId());
    response.setNickName(userData.getNickName());
    response.setAvatarUrl(userData.getAvatarUrl());
    response.setRoleName(userData.getUserRole().getRoleName());
    response.setProfileDescription(userData.getProfileDescription());
    response.setEmail(userData.getEmail());
    response.setFirstName(userData.getFirstName());
    response.setLastName(userData.getLastName());
    response.setAllowProjectApply(userData.isAllowProjectApply());
    response.setAllowProjectCreate(userData.isAllowProjectCreate());
    response.setRoleVerified(userData.isRoleVerified());

    return response;
  }

  private boolean verifyRegistrationData(User user) {

    String username = user.getUserName();
    String password = user.getPassword();
    String nickName = user.getNickName();
    String profileDescription = user.getProfileDescription();
    String roleName = user.getUserRole().getRoleName();
    String firstName = user.getFirstName();
    String lastName = user.getLastName();
    String email = user.getEmail();

    if ((email == null || email.isEmpty())
        || (username == null || username.isEmpty())
        || (password == null || password.isEmpty())
        || (nickName == null || nickName.isEmpty())
        || (profileDescription == null || profileDescription.isEmpty())
        || (roleName == null || roleName.isEmpty())
        || (firstName == null || firstName.isEmpty())
        || (lastName == null || lastName.isEmpty())) {
      return false;
    }

    // check email, username, password pattern
    if (!email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
      return false;
    } else if (!username.matches("^[a-zA-Z0-9]{7,}$")) {
      return false;
    } else return password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{9,}$");
  }


  /**
   * Method to save user data.
   *
   * @param user user data
   * @return String
   */
  public String saveUserData(User user) {
    try {
      userRepository.save(user);
    } catch (Exception e) {
      return "User data saving failed";
    }
    return "User data saved successfully";
  }

  /**
   * Method to validate user data.
   *
   * @param user user data
   * @return String
   */
  public String registerUser(User user) {
    User existingUser = userRepository.findByUserName(user.getUserName());
    if (existingUser != null) {
      return "user already exist";
    }

    // check email is not already registered
    existingUser = userRepository.findByEmail(user.getEmail());
    if (existingUser != null) {
      return "email already registered";
    }
    // verify user data
    boolean isValidUserRegistrationData = verifyRegistrationData(user);
    if (isValidUserRegistrationData) {
      return "user data is valid";
    }
    return "user registration failed, data is not valid";
  }


  public String sendPassCodeEmail(String from, String to, int passcode) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject("Welcome to IdeaSync");

    String emailContentTemplate = """
            歡迎來到IdeaSync,
            
            你的驗證碼為: %d
            
            此驗證碼將在5分鐘後失效請勿將此驗證碼提供給他人
            
            謝謝您的配合
            
            IdeaSync團隊
            """;
    String emailContent = String.format(emailContentTemplate, passcode);

    message.setText(emailContent);
    try {
      emailSender.send(message);
    } catch (Exception e) {
      return "Email sending failed";
    }
    return "Email sent successfully";

  }

  /**
   * Method to get user by id.
   *
   * @param id user id
   * @return UserResponse
   */
  public UserResponse getUser(Long id) {
    User userData = userRepository.findById(id).orElse(null);
    if (userData == null) {
      return null;
    }
    return getUserResponse(userData);
  }

  /**
   * Method to delete user based on username.
   *
   * @param id id of user
   * @return success message
   */
  public String deleteUser(Long id) {
    Optional<User> user = userRepository.findById(id);

    if (user.isPresent()) {
      try {
        userRepository.delete(user.get());
        return "User deleted successfully";
      } catch (Exception e) {
        return "User deletion failed";
      }
    }
    return "User not found";
  }

  public String updateRoleStatus(Long id, boolean status) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      user.get().setRoleVerified(status);
      userRepository.save(user.get());
      return "Role status updated successfully";
    }
    return "User not found";
  }

  public List<UserResponse> getAllUsers() {
    List<User> users = userRepository.findAll();
    List<UserResponse> userResponses = new ArrayList<>();
    for (User user : users) {
      userResponses.add(getUserResponse(user));
    }
    return userResponses;
  }
}
