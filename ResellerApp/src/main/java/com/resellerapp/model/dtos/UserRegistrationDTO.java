package com.resellerapp.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    @Size(min = 3, max = 20)
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @Size(min = 3, max = 20)
    @NotBlank
    private String password;

    @Size(min = 3, max = 20)
    @NotBlank
    private String confirmPassword;
}