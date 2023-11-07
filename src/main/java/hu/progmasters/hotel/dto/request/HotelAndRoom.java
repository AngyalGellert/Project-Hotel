package hu.progmasters.hotel.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class HotelAndRoom {

    @NotNull(message = "Hotel name must not be empty")
    private Long hotelId;

    @NotNull(message = "Hotel address must not be empty")
    @Size(min = 1, max = 200, message = "Hotel address must be between 1 and 200 characters")
    private Long roomId;

}
