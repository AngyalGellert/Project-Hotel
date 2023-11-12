package hu.progmasters.hotel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by szfilep.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomListItem {

    private Long id;
    private String name;
    private Integer numberOfBeds;
    private Integer pricePerNight;
    private String imageUrl;

}
