package hu.progmasters.hotel.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class OauthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String access_token;

    private String token_type;

    private String app_id;

    private int expires_in;

    private LocalDateTime createdTime;

    private LocalDateTime experiedTime;

    public OauthToken() {
        this.createdTime = LocalDateTime.now();
        this.experiedTime = LocalDateTime.now();
    }
}
