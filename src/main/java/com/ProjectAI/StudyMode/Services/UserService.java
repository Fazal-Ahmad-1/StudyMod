package com.ProjectAI.StudyMode.Services;

import com.ProjectAI.StudyMode.Entity.LoginRequest;
import com.ProjectAI.StudyMode.Entity.User;
import com.ProjectAI.StudyMode.Entity.UserDTO;
import com.ProjectAI.StudyMode.Entity.UserRequest;
import com.ProjectAI.StudyMode.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO register(UserRequest userRequest){
        if(userRepository.existsByUsername(userRequest.getUsername())){
            throw new IllegalArgumentException("Username already taken!");
        }

        String ePassword= passwordEncoder.encode(userRequest.getPassword());
        User user=new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(ePassword);
        user.setTimzone(userRequest.getTimezone());

        User saved=userRepository.save(user);

        return UserDTO.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .timezone(saved.getTimzone())
                .build();
    }

    public User login(LoginRequest request){
        User user=userRepository.findByUsername(request.getUsername());
        if(user==null)throw new IllegalArgumentException("No user found with this name");

        boolean passwordMatch=passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!passwordMatch)throw new IllegalArgumentException("Password Incorrect");

        return user;
    }

}
