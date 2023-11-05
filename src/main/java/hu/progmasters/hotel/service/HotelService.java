package hu.progmasters.hotel.service;


import hu.progmasters.hotel.domain.Hotel;
import hu.progmasters.hotel.domain.Room;
import hu.progmasters.hotel.dto.request.HotelAndRoom;
import hu.progmasters.hotel.dto.request.HotelCreateRequest;
import hu.progmasters.hotel.dto.response.HotelAndRoomInfo;
import hu.progmasters.hotel.repository.HotelRepository;
import hu.progmasters.hotel.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;


    public HotelService(HotelRepository hotelRepository,RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.modelMapper = new ModelMapper();
    }

    public void createHotel(HotelCreateRequest hotelCreateRequest) {
        hotelRepository.save(modelMapper.map(hotelCreateRequest, Hotel.class));
    }


    public HotelAndRoomInfo addRoomToHotel(HotelAndRoom hotelAndRoom) {
        Hotel hotel = hotelRepository.findById(hotelAndRoom.getHotelId()).orElseThrow(() -> new IllegalArgumentException("There is no Hotel for this id:" + hotelAndRoom.getHotelId()));
        Room room =  roomRepository.findById(hotelAndRoom.getRoomId()).orElseThrow(() -> new IllegalArgumentException("There is no Room for this id:" + hotelAndRoom.getRoomId()));
        room.setHotel(hotel);
        roomRepository.save(room);
        return new HotelAndRoomInfo(hotel, room);
    }
}
