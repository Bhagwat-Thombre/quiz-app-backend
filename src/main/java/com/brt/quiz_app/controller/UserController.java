package com.brt.quiz_app.controller;

import com.brt.quiz_app.entities.User;
import com.brt.quiz_app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody User user){
        if (userService.hasUserWithEmail(user.getEmail())){
            return new ResponseEntity<>("User already exist", HttpStatus.NOT_ACCEPTABLE);
        }
        User createUser=userService.createUser(user);
        if(createUser==null){
            return new ResponseEntity<>("user not created", HttpStatus.NOT_ACCEPTABLE);

        }
        return new ResponseEntity<>(createUser,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        User dbUser=userService.login(user);
        if(dbUser== null){
            return new ResponseEntity<>("Wrong Credentials",HttpStatus.NOT_ACCEPTABLE);
        }
        return  new ResponseEntity<>(dbUser,HttpStatus.OK);
    }
}

