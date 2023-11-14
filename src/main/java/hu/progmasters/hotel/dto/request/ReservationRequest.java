package hu.progmasters.hotel.dto.request;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class ReservationRequest {

    @NotBlank
    private String guestName;
    @FutureOrPresent(message = "Date must be present date or in the future")
    private LocalDate startDate;
    @Future(message = "Date must in the future")
    private LocalDate endDate;
    @NotNull(message = "Must not be null")
    private Long roomId;
}
