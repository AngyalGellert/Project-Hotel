package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.domain.Reservation;
import hu.progmasters.hotel.dto.request.ReservationModificationRequest;
import hu.progmasters.hotel.dto.request.ReservationRequest;
import hu.progmasters.hotel.dto.response.ReservationDeletedResponse;
import hu.progmasters.hotel.dto.response.ReservationDetails;
import hu.progmasters.hotel.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDetails> save(@Valid @RequestBody ReservationRequest reservationRequest) {
        log.info("Http request, POST /api/reservation, body: " + reservationRequest.toString());
        ReservationDetails reservationDetails = reservationService.recordsReservation(reservationRequest);
        return new ResponseEntity<>(reservationDetails, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationDeletedResponse> delete(@PathVariable("id") Long id) {
        log.info("Http request, DELETE /api/reservation/{Id} with variable: " + id);
        ReservationDeletedResponse reservationDeletedResponse = reservationService.reservationDelete(id);
        return new ResponseEntity<>(reservationDeletedResponse, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<ReservationDetails> updateReservation(@RequestBody @Valid ReservationModificationRequest request){
        log.info("HTTP POST request to api/reservation/update for Reservation with ID: " + request.getId());
        ReservationDetails result = reservationService.updateReservation(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/list/{id}")
    public ResponseEntity<List<ReservationDetails>> userReservationList(@PathVariable("id")Long id){
        return new ResponseEntity<>(reservationService.findReservationByUser(id), HttpStatus.OK);
    }
}
