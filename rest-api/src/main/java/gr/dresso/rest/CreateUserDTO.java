package gr.dresso.rest;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUserDTO {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Size(min=5, max=5)
    private String postalCode;

    @NotEmpty
    private String country;

    @NotEmpty
    private String city;

    @NotEmpty
    private String address;

    @NotEmpty
    @Email
    private String email;

    @DecimalMin("0.0")
    @NotNull
    private double credits;

    @NotEmpty
    private String username;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
    private String password;
}
