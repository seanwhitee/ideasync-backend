package com.ideasync.ideasyncbackend.user;

import com.ideasync.ideasyncbackend.user.dto.PassCodeResponse;
import com.ideasync.ideasyncbackend.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.sql.Timestamp;


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

    // check if user exist
    if (userData == null) {
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
    response.setEmailVerified(userData.isEmailVerified());

    return response;
  }

  private boolean verifyRegistrationData(User user) {
    // verify user data
    String username = user.getUserName();
    String password = user.getPassword();
    String nickName = user.getNickName();
    String profileDescription = user.getProfileDescription();
    String roleName = user.getUserRole().getRoleName();
    String email = user.getEmail();
    String firstName = user.getFirstName();
    String lastName = user.getLastName();

    if ((username == null || username.isEmpty())
        || (password == null || password.isEmpty())
        || (nickName == null || nickName.isEmpty())
        || (profileDescription == null || profileDescription.isEmpty())
        || (roleName == null || roleName.isEmpty())
        || (email == null || email.isEmpty())
        || (firstName == null || firstName.isEmpty())
        || (lastName == null || lastName.isEmpty())) {
      return false;
    }

    // check email, username, password pattern
    if (!username.matches("^[a-zA-Z0-9]{7,}$")) {
      return false;
    } else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{9,}$")) {
      return false;
    } else return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
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

    // check if email is exist
    existingUser = userRepository.findByEmail(user.getEmail());
    if (existingUser != null) {
      return "email already exist";
    }
    // verify user data
    boolean isValidUserRegistrationData = verifyRegistrationData(user);
    if (isValidUserRegistrationData) {
      userRepository.save(user);
      return "user data is valid";
    }
    return "user registration failed, data is not valid";
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
   * Method to send email.
   *
   * @param from     from email
   * @param to       to email
   * @param passcode passcode
   */
  public void sendPassCodeEmail(String from, String to, int passcode) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject("Welcome to IdeaSync");

    String emailContentTemplate = "歡迎來到IdeaSync,\n\n你的驗證碼為: %d\n\n此驗證碼將在5分鐘後失效\n\n請勿將此驗證碼提供給他人\n\n謝謝您的配合\n\nBest,\nIdeaSync團隊";
    String emailContent = String.format(emailContentTemplate, passcode);

    message.setText(emailContent);
    emailSender.send(message);
  }

  /**
   * Method to generate 6 digits random number.
   *
   * @param username username of user
   * @param email    email of user
   * @return passcode and expiration time
   */
  public PassCodeResponse generatePassCode(String username, String email) {
    User user = userRepository.findByUserName(username);
    int passcode = (int) (Math.random() * 900000) + 100000;

    Timestamp passCodeCreateTime = new Timestamp(System.currentTimeMillis());
    Timestamp passCodeExpiryTime = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);


    user.setPassCode(passcode);
    user.setPassCodeCreateTime(passCodeCreateTime);
    userRepository.save(user);
    sendPassCodeEmail("blackseanx@gmail.com", email, passcode);

    return new PassCodeResponse(passcode, passCodeExpiryTime);
  }

  /**
   * Method to delete user based on username.
   *
   * @param username username of user
   * @return success message
   */
  public String deleteUser(String username) {
    User user = userRepository.findByUserName(username);
    // if success, delete and return success message, else return error message
    try {
      userRepository.delete(user);
      return "User deleted successfully";
    } catch (Exception e) {
      return "User deletion failed";
    }
  }


  /**
   * Method to mark user email as verified based on username.
   * @param username username of user
   * @return success message
   */
  public String markEmailAsVerified(String username) {
    User user = userRepository.findByUserName(username);
    user.setEmailVerified(true);
    // if success, save and return success message, else return error message
    try {
      userRepository.save(user);
      return "Email verified successfully";
    } catch (Exception e) {
      return "Email verification failed";
    }
  }
}
