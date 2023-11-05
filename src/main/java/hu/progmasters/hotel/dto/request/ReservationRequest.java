package hu.progmasters.hotel.dto.request;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class ReservationRequest {

    @NotBlank
    private String guestName;
    @PastOrPresent
    private LocalDate startDate;
    @FutureOrPresent
    private LocalDate endDate;
}
