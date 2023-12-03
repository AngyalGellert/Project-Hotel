package hu.progmasters.hotel.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HotelGeocodingResponse {

    private String name;
    private String city;
    private String address;
    private double latitude;
    private double longitude;

}
