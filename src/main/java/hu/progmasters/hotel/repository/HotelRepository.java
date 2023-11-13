package hu.progmasters.hotel.repository;

import hu.progmasters.hotel.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository <Hotel, Long> {

    @Query("SELECT h FROM Hotel h WHERE h.name = :hotelName")
    public Hotel findHotelByName(@Param("hotelName") String hotelName);

}
