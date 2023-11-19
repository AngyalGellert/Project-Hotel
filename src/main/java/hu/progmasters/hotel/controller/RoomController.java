package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.request.ImageUpload;
import hu.progmasters.hotel.dto.request.RoomFormUpdate;
import hu.progmasters.hotel.dto.response.*;
import hu.progmasters.hotel.dto.request.RoomForm;
import hu.progmasters.hotel.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity <List<RoomListItem>> listAllRooms() {
        log.info("Http request, Get /api/rooms");
        List<RoomListItem> result = roomService.getRoomList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity <RoomDetails> roomDetail(@PathVariable("id") Long id) {
        log.info("Http request, Get /api/rooms/{id}");
        return new ResponseEntity<>(roomService.getRoomDetails(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity <RoomDetailsWithReservations> roomDetailWithReservation(@PathVariable("id") Long id) {
        log.info("Http request, Get /api/rooms/{id}/reservations");
        return new ResponseEntity<> (roomService.getRoomDetailsWithReservations(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity <RoomDetails> createRoom(@ModelAttribute @RequestBody @Valid RoomForm roomForm) {
        log.info("Http request, Post /api/rooms, body: " + roomForm.toString());
        RoomDetails result = roomService.createRoom(roomForm);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity <RoomDeletionResponse> deleteRoom(@PathVariable("roomId") Long roomId) {
        log.info("HTTP PUT request to api/rooms/{roomId} with variable: " + roomId);
        RoomDeletionResponse response = roomService.deleteRoom(roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity <RoomDetails> updateRoom(@ModelAttribute @RequestBody @Valid RoomFormUpdate roomFormUpdate) {
        log.info("Http request, Post /api/rooms, body: " + roomFormUpdate.toString());
        return new ResponseEntity<>( roomService.updateRoomValues(roomFormUpdate), HttpStatus.OK);
    }

    @PutMapping("/{roomId}/uploadImage")
    public ResponseEntity<RoomDetails> imageUpload(@ModelAttribute @Valid ImageUpload imageUpload, @PathVariable("roomId") Long roomId) {
        log.info("HTTP PUT request to api/rooms/{roomId}/uploadImage with variable: " + roomId);
        RoomDetails roomDetails = roomService.uploadImage(roomId, imageUpload);
        return new ResponseEntity<>(roomDetails, HttpStatus.OK);
    }

}
