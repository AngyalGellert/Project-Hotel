package hu.progmasters.hotel.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HotelDetails {

    private Long id;
    private String name;
    private String zipCode;
    private String city;
    private String address;
    private int numberOfRooms;
    private List<String> imageUrls;
    private String temperature;
    private String weatherDescription;

    public HotelDetails(Long id, String name, String zipCode, String city, String address, int numberOfRooms) {
        this.id = id;
        this.name = name;
        this.zipCode = zipCode;
        this.city = city;
        this.address = address;
        this.numberOfRooms = numberOfRooms;
    }
}
