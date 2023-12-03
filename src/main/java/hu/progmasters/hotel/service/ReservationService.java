package hu.progmasters.hotel.service;

import hu.progmasters.hotel.domain.Reservation;
import hu.progmasters.hotel.domain.Room;
import hu.progmasters.hotel.domain.User;
import hu.progmasters.hotel.dto.request.ReservationModificationRequest;
import hu.progmasters.hotel.dto.request.ReservationRequest;
import hu.progmasters.hotel.dto.response.ReservationDeletedResponse;
import hu.progmasters.hotel.dto.response.ReservationDetails;
import hu.progmasters.hotel.exception.ReservationAlreadyDeletedException;
import hu.progmasters.hotel.exception.ReservationConflictException;
import hu.progmasters.hotel.exception.ReservationNotFoundException;
import hu.progmasters.hotel.exception.RoomAlreadyDeletedException;
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
    private final RoomService roomService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final EmailSenderService emailSenderService;

    public ReservationDetails recordsReservation(@Valid ReservationRequest request) {
        Room room = roomService.findRoomById(request.getRoomId());
        User userForThisReservation = userService.findUserById(request.getUserId());
        if (room.isDeleted()) {
            throw new RoomAlreadyDeletedException(request.getRoomId());
        } else {
            if (reservationDateValidate(request.getRoomId(), request.getStartDate(), request.getEndDate())) {
                throw new ReservationConflictException("Dátumok ütköznek a már foglalt dátumokkal");
            }
            Reservation reservation = modelMapper.map(request, Reservation.class);
            reservation.setRoom(room);
            reservation.setUser(userForThisReservation);
            reservation.setGuestName(userForThisReservation.getUserName());
            ReservationDetails result = modelMapper.map(reservationRepository.save(reservation), ReservationDetails.class);
            result.setRoomName(room.getName());
            result.setGuestEmail(userForThisReservation.getEmail());
            emailSenderService.sendEmail(room, userForThisReservation);
            return result;
        }
    }

    private boolean reservationDateValidate(Long roomId, LocalDate startDate, LocalDate endDate) {
        List<Reservation> reservations = reservationRepository.findConflictingReservations(roomId);
        return validatingDate(startDate, endDate, reservations);
    }

    public ReservationDeletedResponse reservationDelete(Long id) {
        Reservation reservation = findReservationById(id);
        if (reservationIsNotDeleted(reservation)) {
            reservation.setDeleted(true);
            reservationRepository.save(reservation);
            emailSenderService.sendReservationDeletingEmail(reservation.getUser(), reservation.getRoom());
            return modelMapper.map(reservation, ReservationDeletedResponse.class);
        } else {
            throw new ReservationAlreadyDeletedException(id);
        }
    }

    public ReservationDetails updateReservation(ReservationModificationRequest request) {
        Reservation reservation = findReservationById(request.getId());
        if (reservationUpdateDateValidate(reservation.getId(), reservation.getRoom().getId(), request.getStartDate(), request.getEndDate())) {
            throw new ReservationConflictException("Dátumok ütköznek a már foglalt dátumokkal");
        }

        if (!reservation.isDeleted()) {
            if (reservationIsNotDeleted(reservation)) {
                if (request.getStartDate() != null) {
                    reservation.setStartDate(request.getStartDate());
                }
                if (request.getEndDate() != null) {
                    reservation.setEndDate(request.getEndDate());
                }
                emailSenderService.sendEmail(reservation.getUser(), reservation.getRoom());
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

    public List<ReservationDetails> findReservationByUser(Long id){
        List<Reservation> reservations = reservationRepository.FindAllUserReservation(id);
        List<ReservationDetails> reservationDetails = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDetails.add(modelMapper.map(reservation,ReservationDetails.class));
        }
        return reservationDetails;
    }
}
