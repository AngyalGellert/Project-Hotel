package hu.progmasters.hotel.service;


import hu.progmasters.hotel.domain.Hotel;
import hu.progmasters.hotel.domain.Room;
import hu.progmasters.hotel.dto.request.HotelAndRoom;
import hu.progmasters.hotel.dto.request.HotelCreateRequest;
import hu.progmasters.hotel.dto.response.HotelAndRoomInfo;
import hu.progmasters.hotel.dto.response.HotelCreationResponse;
import hu.progmasters.hotel.dto.response.RoomDetails;
import hu.progmasters.hotel.exception.HotelHasNoRoomsException;
import hu.progmasters.hotel.exception.HotelNotFoundException;
import hu.progmasters.hotel.repository.HotelRepository;
import hu.progmasters.hotel.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    public HotelService(HotelRepository hotelRepository, RoomService roomService, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomService = roomService;
        this.roomRepository = roomRepository;
        this.modelMapper = new ModelMapper();
    }

    public HotelCreationResponse createHotel(HotelCreateRequest hotelCreateRequest) {
        Hotel savedHotel = hotelRepository.save(modelMapper.map(hotelCreateRequest, Hotel.class));
        return modelMapper.map(savedHotel, HotelCreationResponse.class);
    }

    public HotelAndRoomInfo addRoomToHotel(HotelAndRoom hotelAndRoom) {
        Hotel hotel = findHotelById(hotelAndRoom.getHotelId());
        Room room =  roomService.findRoomById(hotelAndRoom.getRoomId());
        room.setHotel(hotel);
        roomRepository.save(room);
        return new HotelAndRoomInfo(hotel, room);
    }

    public List<RoomDetails> listAllRoomsOfHotel(Long hotelId) {
        List<Room> rooms = roomRepository.findAllAvailableRoomsFromHotel(hotelId);
        if (rooms.isEmpty()) {
            throw new HotelHasNoRoomsException(hotelId);
        } else {
            List<RoomDetails> result = new ArrayList<>();
            for (Room room : rooms) {
                result.add(modelMapper.map(room, RoomDetails.class));
            }
            return result;
        }
    }

    public Hotel findHotelById(Long hotelId) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        return hotel.orElseThrow(() -> new HotelNotFoundException(hotelId));
    }
}
