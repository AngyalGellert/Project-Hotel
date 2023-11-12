package hu.progmasters.hotel.dto.response;

import hu.progmasters.hotel.domain.Hotel;
import hu.progmasters.hotel.domain.Room;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelAndRoomInfo {

    private String hotelName;

    private Long roomId;

    public HotelAndRoomInfo(Hotel hotel, Room room) {
        this.hotelName = hotel.getName();
        this.roomId = room.getId();
    }
}
