package com.brt.quiz_app.service.user;

import com.brt.quiz_app.entities.User;

public interface UserService {

    User createUser(User user);
    Boolean hasUserWithEmail(String email);
    User login(User user);
}
