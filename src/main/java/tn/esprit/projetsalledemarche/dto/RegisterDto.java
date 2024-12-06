package tn.esprit.projetsalledemarche.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class RegisterDto {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
}
