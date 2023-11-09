package hu.progmasters.hotel.service;


import hu.progmasters.hotel.domain.Hotel;
import hu.progmasters.hotel.domain.Room;
import hu.progmasters.hotel.dto.request.HotelAndRoom;
import hu.progmasters.hotel.dto.request.HotelCreateRequest;
import hu.progmasters.hotel.dto.response.HotelDetails;
import hu.progmasters.hotel.dto.response.HotelAndRoomInfo;
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


    public HotelAndRoomInfo addRoomToHotel(HotelAndRoom hotelAndRoom) {
        Hotel hotel = hotelRepository.findById(hotelAndRoom.getHotelId()).orElseThrow(() -> new HotelNotFoundException(hotelAndRoom.getHotelId()));
//        Room room =  roomRepository.findById(hotelAndRoom.getRoomId()).orElseThrow(() -> new RoomNotFoundException(hotelAndRoom.getRoomId()));
        Room room =  roomRepository.findById(hotelAndRoom.getRoomId()).orElseThrow(IllegalArgumentException::new);
        room.setHotel(hotel);
        roomRepository.save(room);
        return new HotelAndRoomInfo(hotel, room);
    }
    public List<RoomDetails> listAllRoomsOfHotel(Long hotelId) {
        Hotel hotel = findHotelById(hotelId);
        List<Room> rooms = roomRepository.findAllAvailableRoomsFromHotel(hotelId);

        if (hotel == null) {
            throw new HotelNotFoundException(hotelId);
        } else if (rooms.isEmpty()) {
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

    public List<HotelDetails> listHotelDetails() {
        List<Hotel> hotels = hotelRepository.findAll();
        List<HotelDetails> hotelDetailsList = new ArrayList<>();

        for (Hotel hotel : hotels) {
            HotelDetails hotelDetails = modelMapper.map(hotel, HotelDetails.class);
            hotelDetails.setNumberOfRooms(roomRepository.numberOfAvailableRooms(hotel.getId()));
            hotelDetailsList.add(hotelDetails);
        }
        return hotelDetailsList;
    }
}
