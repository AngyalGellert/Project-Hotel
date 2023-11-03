package hu.progmasters.hotel.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class ReservationRequest {

    @NotNull
    private String guestName;
    @Past
    private LocalDate startDate;
    @Past
    private LocalDate endDate;
}
