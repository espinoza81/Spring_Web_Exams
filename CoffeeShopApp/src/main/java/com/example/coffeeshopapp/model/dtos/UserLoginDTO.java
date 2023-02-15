package com.example.coffeeshopapp.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    @NotBlank
    private String username;

    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    @NotBlank
    private String password;
}