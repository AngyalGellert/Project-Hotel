package hu.progmasters.hotel.service;

import hu.progmasters.hotel.domain.Reservation;
import hu.progmasters.hotel.domain.Room;
import hu.progmasters.hotel.dto.request.RoomFormUpdate;
import hu.progmasters.hotel.dto.response.*;
import hu.progmasters.hotel.dto.request.RoomForm;
import hu.progmasters.hotel.exception.RoomAlreadyDeletedException;
import hu.progmasters.hotel.exception.RoomNotFoundException;
import hu.progmasters.hotel.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by szfilep.
 */
@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<RoomListItem> getRoomList() {
        List<RoomListItem> roomListItems = new ArrayList<>();
        List<Room> rooms = roomRepository.findAll();
        for (Room room : rooms) {
            RoomListItem item = new RoomListItem();
            updateRoomListItemValues(item, room);
            roomListItems.add(item);
        }
        return roomListItems;
    }

    public RoomDetails createRoom(RoomForm roomForm) {
        return modelMapper.map(roomRepository.save(new Room(roomForm)), RoomDetails.class);
    }

    public RoomDetails getRoomDetails(Long roomId) {
        Room room = findRoomById(roomId);
        return modelMapper.map(room, RoomDetails.class);
    }

    private void updateRoomListItemValues(RoomListItem item, Room room) {
        item.setId(room.getId());
        item.setName(room.getName());
        item.setNumberOfBeds(room.getNumberOfBeds());
        item.setPricePerNight(room.getPricePerNight());
        item.setImageUrl(room.getImageUrl());
    }

    public RoomDeletionResponse deleteRoom(Long roomId) {
        Room roomToBeDeleted = findRoomById(roomId);
        if (roomToBeDeleted.isDeleted()) {
            throw new RoomAlreadyDeletedException(roomId);
        } else {
            roomToBeDeleted.setDeleted(true);
            roomRepository.save(roomToBeDeleted);
            RoomDeletionResponse result = modelMapper.map(roomToBeDeleted, RoomDeletionResponse.class);
            result.setDeletionMessage(roomId, roomToBeDeleted.getName());
            return result;
        }
    }

    public RoomDetails updateRoomValues(@Valid RoomFormUpdate roomFormUpdate) {
        Optional<Room> room = roomRepository.findById(roomFormUpdate.getId());
        if (room.isPresent()) {
            if (!roomFormUpdate.getName().isEmpty() || !roomFormUpdate.getName().isBlank() || !roomFormUpdate.getName().equals(room.get().getName())) {
                room.get().setName(roomFormUpdate.getName());
            }
            if (roomFormUpdate.getNumberOfBeds() != room.get().getNumberOfBeds() || roomFormUpdate.getNumberOfBeds() != 0){
                room.get().setNumberOfBeds(roomFormUpdate.getNumberOfBeds());
            }
            if(roomFormUpdate.getPricePerNight() != room.get().getPricePerNight() || roomFormUpdate.getPricePerNight() != 0){
                room.get().setPricePerNight(roomFormUpdate.getPricePerNight());
            }
            if(!roomFormUpdate.getDescription().equals(room.get().getDescription())){
                room.get().setDescription(roomFormUpdate.getDescription());
            }
            if(!roomFormUpdate.getImageUrl().equals(room.get().getImageUrl())){
                room.get().setImageUrl(roomFormUpdate.getImageUrl());
            }
            roomRepository.save(room.get());

        } else {
            throw new RoomNotFoundException(roomFormUpdate.getId());
        }

        return new ModelMapper().map(room.get(), RoomDetails.class);
    }


    public Room findRoomById(Long roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        return roomOptional.orElseThrow(() -> new RoomNotFoundException(roomId));
    }

    public RoomDetailsWithReservations getRoomDetailsWithReservations(Long roomId) {
        Room room = findRoomById(roomId);
        List<ReservationDetails> reservationDetailsList = new ArrayList<>();

        for (Reservation reservation : room.getReservations()) {
            ReservationDetails reservationDetails = modelMapper.map(reservation, ReservationDetails.class);
            reservationDetailsList.add(reservationDetails);
        }

        RoomDetailsWithReservations roomDetailsWithReservations = modelMapper.map(room, RoomDetailsWithReservations.class);
        roomDetailsWithReservations.setReservationDetails(reservationDetailsList);
        return roomDetailsWithReservations;

    }

}
