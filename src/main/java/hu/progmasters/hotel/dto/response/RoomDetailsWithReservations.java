package hu.progmasters.hotel.dto.response;

import hu.progmasters.hotel.domain.Reservation;

import java.util.List;

public class RoomDetailsWithReservations {

    private Long id;

    private String name;

    private Integer numberOfBeds;

    private Integer pricePerNight;

    private String description;

    private String imageUrl;

    private List <ReservationDetails> reservationDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public Integer getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Integer pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<ReservationDetails> getReservationDetails() {
        return reservationDetails;
    }

    public void setReservationDetails(List<ReservationDetails> reservationDetails) {
        this.reservationDetails = reservationDetails;
    }
}
