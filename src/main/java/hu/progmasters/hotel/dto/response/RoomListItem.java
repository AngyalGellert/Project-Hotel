package hu.progmasters.hotel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<String> imageUrl;

}
