package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.request.RoomFormUpdate;
import hu.progmasters.hotel.dto.response.RoomDeletionResponse;
import hu.progmasters.hotel.dto.response.RoomDetails;
import hu.progmasters.hotel.dto.request.RoomForm;
import hu.progmasters.hotel.dto.response.RoomDetailsWithReservations;
import hu.progmasters.hotel.dto.response.RoomListItem;
import hu.progmasters.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by szfilep.
 */
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity <List<RoomListItem>> listAllRooms() {
        List<RoomListItem> result = roomService.getRoomList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity <RoomDetails> roomDetail(@PathVariable("id") Long id) {
        return new ResponseEntity<>(roomService.getRoomDetails(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity <RoomDetailsWithReservations> roomDetailWithReservation(@PathVariable("id") Long id) {
        return new ResponseEntity<> (roomService.getRoomDetailsWithReservations(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity <RoomDetails> createRoom(@RequestBody @Valid RoomForm roomForm) {
        RoomDetails result = roomService.createRoom(roomForm);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") Long roomId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity <RoomDetails> updateRoom(@RequestBody @Valid RoomFormUpdate roomFormUpdate) {
        return new ResponseEntity<>( roomService.updateRoomValues(roomFormUpdate), HttpStatus.OK);
    }

}
