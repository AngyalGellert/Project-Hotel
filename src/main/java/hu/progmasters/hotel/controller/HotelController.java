package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.request.HotelAndRoom;
import hu.progmasters.hotel.dto.request.HotelCreateRequest;
import hu.progmasters.hotel.dto.request.ImageUpload;
import hu.progmasters.hotel.dto.response.*;
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
    public ResponseEntity<HotelCreationResponse> createHotel(@RequestBody @Valid HotelCreateRequest hotelCreateRequest) {
        log.info("Http request, POST /api/hotels, body: " + hotelCreateRequest.toString());
        HotelCreationResponse hotelCreationResponse = hotelService.createHotel(hotelCreateRequest);
        return new ResponseEntity<>(hotelCreationResponse, HttpStatus.CREATED);
    }

    @PostMapping("/addroom")
    public ResponseEntity<HotelAndRoomInfo> addRoomToHotel(@RequestBody @Valid HotelAndRoom hotelAndRoom) {
        log.info("HTTP request, Post api/hotels/addRoom, body: " + hotelAndRoom.toString());
        HotelAndRoomInfo hotelAndRoomInfo = hotelService.addRoomToHotel(hotelAndRoom);
        return new ResponseEntity<>(hotelAndRoomInfo, HttpStatus.OK);
    }


    @GetMapping("/{hotelId}/rooms")
    public ResponseEntity<List<RoomDetails>> listAllRoomsFromHotel(@PathVariable("hotelId") Long hotelId) {
        log.info("HTTP GET request to api/hotels/{hotelId}/rooms with variable: " + hotelId);
        List<RoomDetails> result = hotelService.listAllRoomsOfHotel(hotelId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/listAllHotels")
    public ResponseEntity<List<HotelDetails>> listAll() {
        log.info("HTTP GET request to api/hotels/listAllHotels");
        List<HotelDetails> listedHotelDetails = hotelService.listHotelDetails();
        return new ResponseEntity<>(listedHotelDetails, HttpStatus.OK);
    }

    @PutMapping("/{hotelId}/uploadImage")
    public ResponseEntity<HotelDetails> imageUpload(@ModelAttribute @Valid ImageUpload imageUpload, @PathVariable("hotelId") Long hotelId) {
        log.info("HTTP PUT request to api/hotels/{hotelId}/uploadImage with variable: " + hotelId);
        HotelDetails hotelDetails = hotelService.uploadImage(hotelId, imageUpload);
        return new ResponseEntity<>(hotelDetails, HttpStatus.OK);
    }
    @GetMapping("/getDetails/{hotelId}")
    public ResponseEntity<HotelDetails> getDetailsOfAHotel (@PathVariable("hotelId") Long hotelId) {
        log.info("HTTP GET request to api/hotels/getDetails/{hotelId} with variable: " + hotelId);
        HotelDetails hotelDetails = hotelService.getDetailsFromTheHotel(hotelId);
        return new ResponseEntity<>(hotelDetails, HttpStatus.OK);
    }

    @GetMapping("/getForecast/{hotelId}")
    public ResponseEntity<ForecastResponse> getForecastOfAHotel (@PathVariable("hotelId") Long hotelId) {
        log.info("HTTP GET request to api/hotels/getDetails/{hotelId} with variable: " + hotelId);
        ForecastResponse forecastResponse = hotelService.getForecast(hotelId);
        return new ResponseEntity<>(forecastResponse, HttpStatus.OK);
    }

    @GetMapping("/getGeocoding/{hotelId}")
    public ResponseEntity<HotelGeocodingResponse> getGeocodingDetailsOfAHotel (@PathVariable("hotelId") Long hotelId) {
        log.info("HTTP GET request to api/hotels/getGeocoding/{hotelId} with variable: " + hotelId);
        HotelGeocodingResponse hotelGeocodingResponse = hotelService.getGeocodingDetails(hotelId);
        return new ResponseEntity<>(hotelGeocodingResponse, HttpStatus.OK);
    }

}
