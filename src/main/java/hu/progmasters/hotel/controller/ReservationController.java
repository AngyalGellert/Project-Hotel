package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.request.ReservationRequest;
import hu.progmasters.hotel.dto.response.ReservationDetails;
import hu.progmasters.hotel.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/reservation")
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping()
    public ResponseEntity<ReservationDetails> save(@Valid @RequestBody ReservationRequest reservationRequest) {
        log.info("Http request, POST /api/dwarves, body: " + reservationRequest.toString());
        ReservationDetails reservationDetails = reservationService.recordsReservation(reservationRequest);
        return new ResponseEntity<>(reservationDetails, HttpStatus.CREATED);
    }

}
