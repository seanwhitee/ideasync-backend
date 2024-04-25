package com.ideasync.ideasyncbackend.user;

import com.ideasync.ideasyncbackend.user.dto.RegisterRequest;
import com.ideasync.ideasyncbackend.user.dto.UserResponse;
import com.ideasync.ideasyncbackend.userrole.UserRole;
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

  private boolean verifyRegistrationData(RegisterRequest registerRequest) {

    String username = registerRequest.getUserName();
    String password = registerRequest.getPassword();
    String nickName = registerRequest.getNickName();
    String profileDescription = registerRequest.getProfileDescription();
    String roleName = registerRequest.getRoleName();
    String firstName = registerRequest.getFirstName();
    String lastName = registerRequest.getLastName();

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
    } else return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{9,}$");
  }

  /**
   * Method to validate user data.
   *
   * @param registerRequest user data
   * @return String
   */
  public String registerUser(RegisterRequest registerRequest) {
    User existingUser = userRepository.findByUserName(registerRequest.getUserName());
    if (existingUser != null) {
      return "user already exist";
    }

    // verify user data
    boolean isValidUserRegistrationData = verifyRegistrationData(registerRequest);
    if (isValidUserRegistrationData) {
      User user = new User();
      user.setUserName(registerRequest.getUserName());
      user.setPassword(registerRequest.getPassword());
      user.setNickName(registerRequest.getNickName());
      user.setProfileDescription(registerRequest.getProfileDescription());
      user.setAvatarUrl(registerRequest.getAvatarUrl());
      user.setFirstName(registerRequest.getFirstName());
      user.setLastName(registerRequest.getLastName());
      user.setRoleVerified(registerRequest.getRoleVerified());
      user.setAllowProjectApply(registerRequest.getAllowProjectApply());
      user.setAllowProjectCreate(registerRequest.getAllowProjectCreate());
      UserRole userRole = new UserRole();
      userRole.setId(registerRequest.getRoleId());
      userRole.setRoleName(registerRequest.getRoleName());
      user.setUserRole(userRole);
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
