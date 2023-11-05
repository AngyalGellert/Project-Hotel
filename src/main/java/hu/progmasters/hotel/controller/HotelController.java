package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.request.HotelAndRoom;
import hu.progmasters.hotel.dto.request.HotelCreateRequest;
import hu.progmasters.hotel.dto.response.HotelAndRoomInfo;
import hu.progmasters.hotel.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;


    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity createHotel(@RequestBody @Valid HotelCreateRequest hotelCreateRequest) {
       hotelService.createHotel(hotelCreateRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/addroom")
    public ResponseEntity<HotelAndRoomInfo> addRoomToHotel(@RequestBody @Valid HotelAndRoom hotelAndRoom){
        HotelAndRoomInfo hotelAndRoomInfo = hotelService.addRoomToHotel(hotelAndRoom);
        return new ResponseEntity(hotelAndRoomInfo, HttpStatus.OK);
    }



}
