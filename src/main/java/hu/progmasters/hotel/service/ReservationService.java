package hu.progmasters.hotel.service;

import hu.progmasters.hotel.domain.Reservation;
import hu.progmasters.hotel.domain.Room;
import hu.progmasters.hotel.domain.User;
import hu.progmasters.hotel.dto.request.ReservationModificationRequest;
import hu.progmasters.hotel.dto.request.ReservationRequest;
import hu.progmasters.hotel.dto.response.ReservationDeletedResponse;
import hu.progmasters.hotel.dto.response.ReservationDetails;
import hu.progmasters.hotel.exception.ReservationAlreadyDeletedException;
import hu.progmasters.hotel.exception.ReservationNotFoundException;
import hu.progmasters.hotel.exception.RoomAlreadyDeletedException;
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
    private final RoomService roomService;
    private final UserService userService;
    private final ModelMapper modelMapper;


    public ReservationDetails recordsReservation(@Valid ReservationRequest request) {
        User userForThisReservation = userService.findUserById(request.getUserId());
        Room roomForThisReservation = roomService.findRoomById(request.getRoomId());
        if (roomForThisReservation.isDeleted()) {
            throw new RoomAlreadyDeletedException(request.getRoomId());
        } else {
            Reservation reservation = modelMapper.map(request, Reservation.class);
            reservation.setRoom(roomForThisReservation);
            reservation.setUser(userForThisReservation);
            reservation.setGuestName(userForThisReservation.getUserName());
            ReservationDetails result = modelMapper.map(reservationRepository.save(reservation), ReservationDetails.class);
            result.setRoomName(roomForThisReservation.getName());
            result.setGuestEmail(userForThisReservation.getEmail());
            return result;
        }
    }

    public ReservationDeletedResponse reservationDelete(Long id) {
        Reservation reservation = findReservationById(id);
        if (reservationIsNotDeleted(reservation)) {
            reservation.setDeleted(true);
            reservationRepository.save(reservation);
            return modelMapper.map(reservation, ReservationDeletedResponse.class);
        } else {
            throw new ReservationAlreadyDeletedException(id);
        }
    }

    public ReservationDetails updateReservation(ReservationModificationRequest request) {
        Reservation reservation = findReservationById(request.getId());
        if (!reservation.getRoom().isDeleted()) {
            if (reservationIsNotDeleted(reservation)) {
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
        } else {
            throw new RoomAlreadyDeletedException(reservation.getRoom().getId());
        }
        ReservationDetails result = modelMapper.map(reservation, ReservationDetails.class);
        result.setRoomName(reservation.getRoom().getName());
        result.setGuestEmail(reservation.getUser().getEmail());
        return result;
    }

    private boolean reservationIsNotDeleted(Reservation reservation) {
        if (!reservation.isDeleted()) {
            return true;
        } else {
            return false;
        }
    }

    public Reservation findReservationById(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        return reservation.orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }
}
