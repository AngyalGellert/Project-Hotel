package hu.progmasters.hotel.dto.response;

import hu.progmasters.hotel.config.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfo {

    private Long id;

    private String userName;

    private String email;

    private String password;

    private Role role;



}
