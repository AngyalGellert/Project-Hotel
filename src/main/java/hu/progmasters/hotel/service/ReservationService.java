package hu.progmasters.hotel.service;

import hu.progmasters.hotel.domain.Reservation;
import hu.progmasters.hotel.dto.request.ReservationRequest;
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
@Slf4j
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;


    public ReservationDetails recordsReservation(@Valid ReservationRequest reservation) {
        Reservation newReservation = modelMapper.map(reservation, Reservation.class);
        Reservation saved = reservationRepository.save(newReservation);
        return modelMapper.map(saved, ReservationDetails.class);
    }

    public void reservationDelete(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setDeleted(true);
            reservationRepository.save(reservation);
        } else {
            throw new ReservationNotFoundException(id);
        }
    }
}
