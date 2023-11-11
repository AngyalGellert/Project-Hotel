package hu.progmasters.hotel.dto.request;

import hu.progmasters.hotel.config.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegistrationForm {
    @NotNull
    @Size(min = 3, max = 200)
    private String userName;

    @Size(min = 3, max = 200)
    private String email;

    @Size(min = 3, max = 200)
    private String password;


}
