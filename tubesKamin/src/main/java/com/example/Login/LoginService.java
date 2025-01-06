package com.example.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    
    @Autowired
    private LoginRepository loginRepository;

    public LoginData login(String username, String password){
        if(loginRepository.findUser(username).isPresent()){
            LoginData user = loginRepository.findUser(username).get();
            
            //BAD PRACTICE
            if(password.equals(user.getPasswords())){
                return user;
            }
        }
        return null;
    }
}
