package hu.progmasters.hotel.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HotelCreationResponse {

    private String name;
    private String address;
    private List <String> imageUrls;

}
