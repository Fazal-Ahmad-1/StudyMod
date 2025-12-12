package com.ProjectAI.StudyMode.Controllers;

import com.ProjectAI.StudyMode.Entity.LoginRequest;
import com.ProjectAI.StudyMode.Entity.User;
import com.ProjectAI.StudyMode.Entity.UserRequest;
import com.ProjectAI.StudyMode.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request){
        return new ResponseEntity<>(userService.register(request), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        User user = userService.login(request);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
