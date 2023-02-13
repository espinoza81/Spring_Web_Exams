package com.softuni.battleships.session;

import com.softuni.battleships.models.User;
import com.softuni.battleships.models.dtos.UserLoginDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class LoggedUser {
    private Long id;
    private String fullName;

    public LoggedUser() {
    }

    public void login(User user){
        this.id = user.getId();
        this.fullName = user.getFullName();
    }

    public void logout(){
        this.id = null;
        this.fullName = null;
    }

    public Long getId() {
        return id;
    }

    public LoggedUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public LoggedUser setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
