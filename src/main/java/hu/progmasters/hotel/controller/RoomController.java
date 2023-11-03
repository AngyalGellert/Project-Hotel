package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.request.RoomFormUpdate;
import hu.progmasters.hotel.dto.response.RoomDetails;
import hu.progmasters.hotel.dto.request.RoomForm;
import hu.progmasters.hotel.dto.response.RoomDetailsWithReservations;
import hu.progmasters.hotel.dto.response.RoomListItem;
import hu.progmasters.hotel.service.HotelService;
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

    private HotelService hotelService;

    @Autowired
    public RoomController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<RoomListItem> rooms() {
        return hotelService.getRoomList();
    }

    @GetMapping("/{id}")
    public RoomDetails roomDetail(@PathVariable("id") Long id) {
        return hotelService.getRoomDetails(id);
    }

    @GetMapping("/{id}/reservations")
    public RoomDetailsWithReservations roomDetailWithReservation(@PathVariable("id") Long id) {
        return hotelService.getRoomDetailsWithReservations(id);
    }

    @PostMapping
    public ResponseEntity createRoom(@RequestBody @Valid RoomForm roomForm) {
        hotelService.createRoom(roomForm);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity deleteRoom(@PathVariable("id") Long roomId) {
        hotelService.deleteRoom(roomId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity <RoomDetails> updateRoom(@RequestBody @Valid RoomFormUpdate roomFormUpdate) {
        return new ResponseEntity( hotelService.updateRoomValues(roomFormUpdate),HttpStatus.OK);
    }

}
