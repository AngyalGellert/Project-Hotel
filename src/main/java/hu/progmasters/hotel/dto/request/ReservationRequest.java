package hu.progmasters.hotel.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class ReservationRequest {

    @NotNull
    private String guestName;
    @PastOrPresent
    private LocalDate startDate;
    @PastOrPresent
    private LocalDate endDate;
}
