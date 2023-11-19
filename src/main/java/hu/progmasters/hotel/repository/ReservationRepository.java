package hu.progmasters.hotel.repository;

import hu.progmasters.hotel.domain.Reservation;
import hu.progmasters.hotel.dto.request.ReservationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by szfilep.
 */
@Repository

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId AND r.startDate >= CURRENT_DATE")
    List<Reservation> findConflictingReservations(@Param("roomId") Long roomId);

}
