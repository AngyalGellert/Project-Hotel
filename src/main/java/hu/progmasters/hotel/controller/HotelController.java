package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.request.HotelAndRoom;
import hu.progmasters.hotel.dto.request.HotelCreateRequest;
import hu.progmasters.hotel.dto.response.HotelAndRoomInfo;
import hu.progmasters.hotel.dto.response.HotelCreationResponse;
import hu.progmasters.hotel.dto.response.RoomDetails;
import hu.progmasters.hotel.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@Slf4j
public class HotelController {

    private final HotelService hotelService;


    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity <HotelCreationResponse> createHotel(@RequestBody @Valid HotelCreateRequest hotelCreateRequest) {
       HotelCreationResponse hotelCreationResponse = hotelService.createHotel(hotelCreateRequest);
        return new ResponseEntity<>(hotelCreationResponse, HttpStatus.CREATED);
    }

    @PostMapping("/addroom")
    public ResponseEntity<HotelAndRoomInfo> addRoomToHotel(@RequestBody @Valid HotelAndRoom hotelAndRoom){
        HotelAndRoomInfo hotelAndRoomInfo = hotelService.addRoomToHotel(hotelAndRoom);
        return new ResponseEntity<>(hotelAndRoomInfo, HttpStatus.OK);
    }


    @GetMapping("/{hotelId}/rooms")
    public ResponseEntity<List<RoomDetails>> listAllRoomsFromHotel(@PathVariable("hotelId") Long hotelId){
        log.info("HTTP GET request to api/hotels/{hotelId}/rooms with variable: " + hotelId);
        List<RoomDetails> result = hotelService.listAllRoomsOfHotel(hotelId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
