package hu.progmasters.hotel.repository;

import hu.progmasters.hotel.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by szfilep.
 */
@Repository

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId " +
            "AND ((r.startDate <= :startDate AND r.endDate >= :startDate) " +
            "OR (r.startDate <= :endDate AND r.endDate >= :endDate))")
    List<Reservation> findConflictingReservations(@Param("roomId") Long roomId,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);


}
