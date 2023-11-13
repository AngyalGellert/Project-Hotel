package hu.progmasters.hotel.repository;

import hu.progmasters.hotel.domain.Hotel;
import hu.progmasters.hotel.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by szfilep.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.isDeleted = false AND r.hotel.id = :hotelId")
    List<Room> findAllAvailableRoomsFromHotel(@Param("hotelId") Long hotelId);

    @Query("SELECT COUNT (r) FROM Room r WHERE r.isDeleted = false AND r.hotel.id = :hotelId")
    int numberOfAvailableRooms(@Param("hotelId") Long hotelId);

    @Query("SELECT r FROM Room r WHERE r.name = :roomName")
    public Room findRoomByName(@Param("roomName") String roomName);

}
