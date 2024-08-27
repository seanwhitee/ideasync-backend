package com.ideasync.ideasyncbackend.user;


import com.ideasync.ideasyncbackend.applicant.ApplicantRepository;
import com.ideasync.ideasyncbackend.comment.CommentRepository;
import com.ideasync.ideasyncbackend.user.dto.RegisterRequest;

import com.ideasync.ideasyncbackend.user.dto.UserResponse;
import com.ideasync.ideasyncbackend.userrole.UserRole;
import com.ideasync.ideasyncbackend.userrole.UserRoleRepository;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



/**
 * Service class for user operations.
 */
@Service
public class UserService {

  private final UserRepository userRepository;
  private final JavaMailSender emailSender;
  private final CommentRepository commentRepository;
  private final ApplicantRepository applicantRepository;
  private final UserRoleRepository userRoleRepository;
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  public UserService(UserRepository userRepository, JavaMailSender emailSender, CommentRepository commentRepository, ApplicantRepository applicantRepository, UserRoleRepository userRoleRepository) {
    this.userRepository = userRepository;
    this.emailSender = emailSender;
    this.commentRepository = commentRepository;
    this.applicantRepository = applicantRepository;
    this.userRoleRepository = userRoleRepository;
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

  public UserResponse getUserResponse(User userData) {
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
   * @param userReq user request data
   * @return String
   */
  public String saveUserData(RegisterRequest userReq) {
    UserRole userRole = userRoleRepository.findUserRoleByRoleName(userReq.getRoleName());

    User user = new User();
    user.setUserName(userReq.getUserName());
    user.setPassword(userReq.getPassword());
    user.setNickName(userReq.getNickName());
    user.setProfileDescription(userReq.getProfileDescription());
    user.setUserRole(userRole);
    user.setFirstName(userReq.getFirstName());
    user.setLastName(userReq.getLastName());
    user.setEmail(userReq.getEmail());
    user.setAvatarUrl(userReq.getAvatarUrl());
    user.setAllowProjectApply(userReq.isAllowProjectApply());
    user.setAllowProjectCreate(userReq.isAllowProjectCreate());
    user.setRoleVerified(userReq.isRoleVerified());

    try {
      userRepository.save(user);
    } catch (Exception e) {
      logger.error("User data saving failed", e);
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
    try {

      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject("Welcome to IdeaSync");

      String emailContent = """
              <html>
                <head>
                  <style>
                    body {
                      font-family: Arial, sans-serif;
                      background-color: #f4f4f4;
                      margin: 0;
                      padding: 0;
                      color: #333333;
                    }
                    .container {
                      background-color: #ffffff;
                      margin: 50px auto;
                      padding: 20px;
                      border-radius: 10px;
                      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                      max-width: 600px;
                    }
                    h1 {
                      color: #5b21b6;
                    }
                    p {
                      font-size: 16px;
                      line-height: 1.5;
                    }
                    .code {
                      font-size: 24px;
                      font-weight: bold;
                      color: #333333;
                      background-color: #f2f2f2;
                      padding: 10px;
                      border-radius: 5px;
                      display: inline-block;
                      margin: 20px 0;
                    }
                    .footer {
                      margin-top: 30px;
                      font-size: 14px;
                      color: #777777;
                    }
                  </style>
                </head>
                <body>
                  <div class="container">
                    <h1>Verification Code</h1>
                    <p>Please use the verification code below to sign up.</p>
                    <p>This code is valid for 5 minutes.</p>
                    <div class="code">%d</div>
                    <p>If you didn’t request this, you can ignore this email.</p>
                    <p class="footer">Thanks,<br />The IdeaSync team</p>
                  </div>
                </body>
              </html>
              """;

      emailContent = String.format(emailContent, passcode);
      helper.setText(emailContent, true); // Set to true to indicate HTML content

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
  public UserResponse getUser(UUID id) {
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
  public String deleteUser(UUID id) {
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

  public String updateRoleStatus(UUID id, boolean status) {
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

  public int countUserComments(UUID userId) {
    return commentRepository.findCommentsByUser(userRepository.findUserById(userId)).size();

  }

  public int countAccept(UUID userId) {
    return applicantRepository.findApplicantsByUserAndVerified(userRepository.findUserById(userId), 1).size();
  }
}
