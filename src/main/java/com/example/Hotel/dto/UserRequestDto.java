package com.example.Hotel.dto;

import com.example.Hotel.entity.Role;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@FieldNameConstants
public class UserRequestDto {
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String name;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
    private Set<String> roles;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
