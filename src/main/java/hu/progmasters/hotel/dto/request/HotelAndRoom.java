package hu.progmasters.hotel.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class HotelAndRoom {

    @NotNull(message = "Hotel ID must not be null")
    private Long hotelId;

    @NotNull(message = "Room ID must not be null")
    private Long roomId;

}
