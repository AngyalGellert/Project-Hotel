package hu.progmasters.hotel.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelDetails {

    private Long id;
    private String name;
    private String address;
    private int numberOfRooms;

}
