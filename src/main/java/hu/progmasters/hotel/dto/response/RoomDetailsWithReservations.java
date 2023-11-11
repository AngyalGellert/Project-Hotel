package hu.progmasters.hotel.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class RoomDetailsWithReservations {

    private Long id;
    private String name;
    private Integer numberOfBeds;
    private Integer pricePerNight;
    private String description;
    private String imageUrl;
    private List <ReservationDetails> reservationDetails;

}
