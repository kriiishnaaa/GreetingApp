package com.GreetingApp.GreetingApp.Service;

import com.GreetingApp.GreetingApp.DTO.AuthUserDTO;
import com.GreetingApp.GreetingApp.DTO.LoginDTO;
import com.GreetingApp.GreetingApp.Model.AuthUser;

public interface IAuthenticationService {
    AuthUser register(AuthUserDTO userDTO) throws Exception;

    String login(LoginDTO loginDTO);

}