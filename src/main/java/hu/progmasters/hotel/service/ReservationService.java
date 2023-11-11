package hu.progmasters.hotel.service;

import hu.progmasters.hotel.domain.Reservation;
import hu.progmasters.hotel.dto.request.ReservationRequest;
import hu.progmasters.hotel.dto.response.ReservationDeletedResponse;
import hu.progmasters.hotel.dto.response.ReservationDetails;
import hu.progmasters.hotel.exception.ReservationNotFoundException;
import hu.progmasters.hotel.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;


    public ReservationDetails recordsReservation(@Valid ReservationRequest reservation) {
        Reservation newReservation = modelMapper.map(reservation, Reservation.class);
        Reservation saved = reservationRepository.save(newReservation);
        return modelMapper.map(saved, ReservationDetails.class);
    }

    public ReservationDeletedResponse reservationDelete(Long id) {
        Reservation reservation = findReservationById(id);
        if (!reservation.isDeleted()) {
            reservation.setDeleted(true);
            reservationRepository.save(reservation);
            return modelMapper.map(reservation, ReservationDeletedResponse.class);
        } else {
//            throw new ReservationAlreadyDeletedException(id);
            throw new IllegalArgumentException();
        }
    }

    public Reservation findReservationById(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        return reservation.orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }
}
