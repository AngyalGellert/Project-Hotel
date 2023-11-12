package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.request.RoomFormUpdate;
import hu.progmasters.hotel.dto.response.RoomDeletionResponse;
import hu.progmasters.hotel.dto.response.RoomDetails;
import hu.progmasters.hotel.dto.request.RoomForm;
import hu.progmasters.hotel.dto.response.RoomDetailsWithReservations;
import hu.progmasters.hotel.dto.response.RoomListItem;
import hu.progmasters.hotel.service.RoomService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RoomController {

    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<RoomListItem> rooms() {
        return roomService.getRoomList();
    }

    @GetMapping("/{id}")
    public RoomDetails roomDetail(@PathVariable("id") Long id) {
        return roomService.getRoomDetails(id);
    }

    @GetMapping("/{id}/reservations")
    public RoomDetailsWithReservations roomDetailWithReservation(@PathVariable("id") Long id) {
        return roomService.getRoomDetailsWithReservations(id);
    }

    @PostMapping
    public ResponseEntity createRoom(@RequestBody @Valid RoomForm roomForm) {
        roomService.createRoom(roomForm);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<RoomDeletionResponse> deleteRoom(@PathVariable("roomId") Long roomId) {
        log.info("HTTP PUT request to api/rooms/{roomId} with variable: " + roomId);
        RoomDeletionResponse response = roomService.deleteRoom(roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity <RoomDetails> updateRoom(@RequestBody @Valid RoomFormUpdate roomFormUpdate) {
        return new ResponseEntity( roomService.updateRoomValues(roomFormUpdate),HttpStatus.OK);
    }

}
