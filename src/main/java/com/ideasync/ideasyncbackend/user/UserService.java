package com.ideasync.ideasyncbackend.user;

import com.fasterxml.jackson.databind.util.JSONPObject;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;


@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;

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

    if ((username.isEmpty() || username == null)
    || (password.isEmpty() || password == null)
    || (nickName.isEmpty() || nickName == null)
    || (profileDescription.isEmpty() || profileDescription == null)
    || (roleName.isEmpty() || roleName == null)
    || (email.isEmpty() || email == null)
    || (firstName.isEmpty() || firstName == null)
    || (lastName.isEmpty() || lastName == null)) {
      return false;
    }

    // check email, username, password pattern
    if (!username.matches("^[a-zA-Z0-9]{7,}$")) {
      return false;
    } else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{9,}$")) {
      return false;
    } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
      return false;
    }

    // use buildin verifier to verify email

    return true;
  }
  public String registerUser(User user) {
    // verify user data
    Boolean isValidUserRegistrationData = verifyRegistrationData(user);
    if (isValidUserRegistrationData) {
      userRepository.save(user);
      return "User registered successfully";
    }
    return "User registration failed, data is not valid";
  }

  public UserResponse getUser(Long id) {
    User userData = userRepository.findById(id).orElse(null);
    if (userData == null) {
      return null;
    }
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
}
