package hu.progmasters.hotel.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data

public class HotelCreateRequest {

    @NotNull(message = "Hotel name must not be empty")
    @Size(min = 1, max = 200, message = "Hotel name must be between 1 and 200 characters")
    private String name;

    @NotNull(message = "Hotel's city name must not be empty")
    @Size(min = 1, max = 200, message = "Hotel's city name must be between 1 and 200 characters")
    private String city;

    @NotNull(message = "Hotel address must not be empty")
    @Size(min = 1, max = 200, message = "Hotel address must be between 1 and 200 characters")
    private String address;

    @NotNull(message = "Hotel zipCode must not be empty")
    @Size(min = 1, max = 200, message = "Hotel zipCode must be between 1 and 200 characters")
    private String zipCode;

}
