package hu.progmasters.hotel.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by szfilep.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetails {

    private Long id;
    private String name;
    private Integer numberOfBeds;
    private Integer pricePerNight;
    private String description;
    private List<String> imageUrls;

}
