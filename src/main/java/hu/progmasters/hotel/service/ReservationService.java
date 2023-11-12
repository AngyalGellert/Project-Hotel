package hu.progmasters.hotel.service;

import hu.progmasters.hotel.domain.Reservation;
import hu.progmasters.hotel.dto.request.ReservationModificationRequest;
import hu.progmasters.hotel.dto.request.ReservationRequest;
import hu.progmasters.hotel.dto.response.ReservationDeletedResponse;
import hu.progmasters.hotel.dto.response.ReservationDetails;
import hu.progmasters.hotel.exception.ReservationAlreadyDeletedException;
import hu.progmasters.hotel.exception.ReservationNotFoundException;
import hu.progmasters.hotel.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
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

    public ReservationDeletedResponse reservationDelete(Long id) {
        Reservation reservation = findReservationById(id);
        if (!reservation.isDeleted()) {
            reservation.setDeleted(true);
            reservationRepository.save(reservation);
            return modelMapper.map(reservation, ReservationDeletedResponse.class);
        } else {
            throw new ReservationAlreadyDeletedException(id);
        }
    }

    public ReservationDetails updateReservation(ReservationModificationRequest request) {
        Reservation reservation = findReservationById(request.getId());
        if (!reservation.isDeleted()) {
            if (!request.getGuestName().isBlank()) {
                reservation.setGuestName(request.getGuestName());
            }
            if (request.getStartDate() != null) {
                reservation.setStartDate(request.getStartDate());
            }
            if (request.getEndDate() != null) {
                reservation.setEndDate(request.getEndDate());
            }
            reservationRepository.save(reservation);
        } else {
            throw new ReservationAlreadyDeletedException(reservation.getId());
        }
        return modelMapper.map(reservation, ReservationDetails.class);
    }

    private boolean reservationIsValid(Long reservationId) {
        Reservation reservation = findReservationById(reservationId);
        if (reservation.isDeleted()) {
            throw new ReservationAlreadyDeletedException(reservationId);
        } else {
            return true;
        }
    }

    public Reservation findReservationById(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        return reservation.orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }
}
