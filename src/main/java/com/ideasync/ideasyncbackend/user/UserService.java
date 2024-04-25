package com.ideasync.ideasyncbackend.user;

import com.ideasync.ideasyncbackend.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service class for user operations.
 */
@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
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

    if ((username == null || username.isEmpty())
        || (password == null || password.isEmpty())
        || (nickName == null || nickName.isEmpty())
        || (profileDescription == null || profileDescription.isEmpty())
        || (roleName == null || roleName.isEmpty())
        || (firstName == null || firstName.isEmpty())
        || (lastName == null || lastName.isEmpty())) {
      return false;
    }

    // check email, username, password pattern
    if (!username.matches("^[a-zA-Z0-9]{7,}$")) {
      return false;
    } else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{9,}$")) {
      return false;
    }
    return true;
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
   * Method to delete user based on username.
   *
   * @param username username of user
   * @return success message
   */
  public String deleteUser(String username) {
    User user = userRepository.findByUserName(username);

    try {
      userRepository.delete(user);
      return "User deleted successfully";
    } catch (Exception e) {
      return "User deletion failed";
    }
  }
}
