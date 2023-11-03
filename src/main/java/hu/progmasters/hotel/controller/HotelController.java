package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.request.HotelCreateRequest;
import hu.progmasters.hotel.dto.request.RoomForm;
import hu.progmasters.hotel.service.HotelService;
import org.modelmapper.ModelMapper;
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

    private HotelService hotelService;
    private final ModelMapper modelMapper;

    public HotelController(HotelService hotelService, ModelMapper modelMapper) {
        this.hotelService = hotelService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity createHotel(@RequestBody @Valid HotelCreateRequest hotelCreateRequest) {
        hotelService.createHotel(hotelCreateRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }


}
