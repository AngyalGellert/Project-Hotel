package hu.progmasters.hotel.service;

import hu.progmasters.hotel.domain.Reservation;
import hu.progmasters.hotel.domain.Room;
import hu.progmasters.hotel.dto.request.ReservationModificationRequest;
import hu.progmasters.hotel.dto.request.ReservationRequest;
import hu.progmasters.hotel.dto.response.ReservationDeletedResponse;
import hu.progmasters.hotel.dto.response.ReservationDetails;
import hu.progmasters.hotel.exception.ReservationAlreadyDeletedException;
import hu.progmasters.hotel.exception.ReservationConflictException;
import hu.progmasters.hotel.exception.ReservationNotFoundException;
import hu.progmasters.hotel.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;
    private final RoomService roomService;


    public ReservationDetails recordsReservation(@Valid ReservationRequest reservation) {
        Room room = roomService.findRoomById(reservation.getRoomId());
        if (reservationDateValidate(reservation.getRoomId(),reservation.getStartDate(), reservation.getEndDate())) {
            throw new ReservationConflictException("Dátumok ütköznek a már foglalt dátumokkal");
        }
        Reservation newReservation = modelMapper.map(reservation, Reservation.class);
        newReservation.setRoom(room);
        Reservation saved = reservationRepository.save(newReservation);
        return modelMapper.map(saved, ReservationDetails.class);
    }

    private boolean reservationDateValidate(Long roomId, LocalDate startDate, LocalDate endDate) {
        List<Reservation> reservations = reservationRepository.findConflictingReservations(roomId);
        return validatingDate(startDate, endDate, reservations);
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
        if (reservationUpdateDateValidate(reservation.getId(),reservation.getRoom().getId(), request.getStartDate(), request.getEndDate())) {
            throw new ReservationConflictException("Dátumok ütköznek a már foglalt dátumokkal");
        }

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

    private boolean reservationUpdateDateValidate(Long reservationId, Long roomId, LocalDate startDate, LocalDate endDate) {
        List<Reservation> reservations = reservationRepository.findConflictingReservations(roomId);
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId() == reservationId) {
                reservations.remove(i);
            }
        }
        return validatingDate(startDate, endDate, reservations);
    }

    private static boolean validatingDate(LocalDate startDate, LocalDate endDate, List<Reservation> reservations) {
        for (int i = 0; i < reservations.size(); i++) {
            int helpDayNumbers = (int) ChronoUnit.DAYS.between(reservations.get(i).getStartDate(), reservations.get(i).getEndDate()) + 1;
            List<LocalDate> dates = new ArrayList<>();
            for (int j = 0; j < helpDayNumbers; j++) {
                dates.add(reservations.get(i).getStartDate().plusDays(j));
            }
            if (dates.contains(startDate)) {
                return true;
            }
            if (dates.contains(endDate)) {
                return true;
            }
        }
        return false;
    }

    public Reservation findReservationById(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        return reservation.orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }

}