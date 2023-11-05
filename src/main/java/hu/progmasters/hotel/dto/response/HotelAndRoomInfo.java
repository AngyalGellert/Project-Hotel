package hu.progmasters.hotel.dto.response;

import hu.progmasters.hotel.domain.Hotel;
import hu.progmasters.hotel.domain.Room;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class HotelAndRoomInfo {

    private Hotel hotel;

    private Room room;

    public HotelAndRoomInfo(Hotel hotel, Room room) {
        this.hotel = hotel;
        this.room = room;
    }
}
