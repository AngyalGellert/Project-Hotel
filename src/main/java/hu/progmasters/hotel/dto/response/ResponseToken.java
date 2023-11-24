package hu.progmasters.hotel.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
public class ResponseToken {

    private String access_token;

    private String token_type;

    private String app_id;

    private LocalDateTime expires_in;


}
