package com.example.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserLoginRepository userLoginRepository;

    @Autowired
    public UserService(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

    public UserLogin authenticate(String username, String password) {
        UserLogin user = userLoginRepository.findByUsername(username);
        if (user != null && user.getPassword().trim().equals(password.trim())) {
            return user;
        }
        return null;
    }
}
