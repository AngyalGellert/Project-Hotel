package hu.progmasters.hotel.domain;

import hu.progmasters.hotel.config.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 200)
    private String userName;

    @Size(min = 3, max = 200)
    @Column(unique = true)
    @Email
    private String email;

    @Size(min = 3, max = 200)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


}
