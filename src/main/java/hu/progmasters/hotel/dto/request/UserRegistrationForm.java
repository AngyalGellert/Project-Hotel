package hu.progmasters.hotel.dto.request;

import hu.progmasters.hotel.config.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegistrationForm {
    @NotNull(message = "Username must not be null.")
    @NotBlank(message = "Username must not be blank.")
    @Size(min = 3, max = 200, message = "Username min 3 character")
    private String userName;

    @Size(min = 3, max = 200, message = "Email min 3 character")
    @Email(message = "Please enter a valid email.")
    @NotNull(message = "Email must not be null.")
    @NotBlank(message = "Email must not be blank.")
    private String email;


    @NotBlank(message = "Password must not be blank.")
    @NotNull(message = "Password must not be null.")
    @Size(min = 3, max = 200, message = "Password min 3 character")
    private String password;


}
