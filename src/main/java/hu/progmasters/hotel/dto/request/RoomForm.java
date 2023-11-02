package hu.progmasters.hotel.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomForm {

    @NotNull(message = "Room name must not be empty")
    @Size(min = 1, max = 200, message = "Room name must be between 1 and 200 characters")
    private String name;

    @NotNull(message = "Number of beds must be between 1 and 50")
    @Min(value = 1, message = "Number of beds must be between 1 and 50")
    @Max(value = 50, message = "Number of beds must be between 1 and 50")
    private Integer numberOfBeds;

    @NotNull(message = "Price must be between 1 and 1000000")
    @Min(value = 1, message = "Price must be between 1 and 1000000")
    @Max(value = 1000000, message = "Price must be between 1 and 1000000")
    private Integer pricePerNight;

    private String description;
    private String imageUrl;


}
