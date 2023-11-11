package hu.progmasters.hotel.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomFormUpdate {

    @NotNull(message = "Room Id must not be empty")
    private Long id;

    private String name;

    @Positive (message = "The value must be positive")
    private Integer numberOfBeds;

    @Positive(message = "The value must be positive")
    private Integer pricePerNight;

    private String description;

    private String imageUrl;

}
