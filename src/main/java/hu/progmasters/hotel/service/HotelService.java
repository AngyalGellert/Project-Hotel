package hu.progmasters.hotel.service;


import hu.progmasters.hotel.domain.Hotel;
import hu.progmasters.hotel.domain.Room;
import hu.progmasters.hotel.dto.request.HotelCreateRequest;
import hu.progmasters.hotel.dto.response.RoomDetails;
import hu.progmasters.hotel.exception.HotelHasNoRoomsException;
import hu.progmasters.hotel.exception.HotelNotFoundException;
import hu.progmasters.hotel.repository.HotelRepository;
import hu.progmasters.hotel.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;


    public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.modelMapper = new ModelMapper();
    }

    public void createHotel(HotelCreateRequest hotelCreateRequest) {
        hotelRepository.save(modelMapper.map(hotelCreateRequest, Hotel.class));
    }


    public List<RoomDetails> listAllRoomsOfHotel(Long hotelId) {
        List<Room> rooms = roomRepository.findAllRoomsFromHotel(hotelId);
        List<RoomDetails> result = new ArrayList<>();
        if (rooms.isEmpty() || checkIfHotelExistsById(hotelId)) {
            throw new HotelHasNoRoomsException(hotelId);
        } else {
            for (Room room : rooms) {
                if (!room.isDeleted()) {
                    result.add(modelMapper.map(room, RoomDetails.class));
                }
            }
        }
        return result;
    }

    private boolean checkIfHotelExistsById(Long hotelId) {
        findHotelById(hotelId);
        return false;
    }

    public Hotel findHotelById(Long hotelId){
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        return hotel.orElseThrow(() -> new HotelNotFoundException(hotelId));
    }
}
