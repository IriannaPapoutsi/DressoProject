package gr.dresso.rest.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUserDTO {

    // TODO: Rename this package to have non-capitalized letters: DTO -> dto. This is a naming convention for java.

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

    // TODO: Credits should not be passed when creating a user. It should have a fixed value of 15 when creating a user, as mentioned on our exercise.
    @DecimalMin("0.0")
    @NotNull
    private double credits;

    @NotEmpty
    private String username;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
    private String password;
}
