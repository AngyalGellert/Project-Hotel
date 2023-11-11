package hu.progmasters.hotel.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDeletedResponse {

    private Long id;
    private boolean deleted;

}
