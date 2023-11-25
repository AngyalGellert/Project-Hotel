package hu.progmasters.hotel.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationModificationRequest {
    @NotNull(message = "ID must not be blank")
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

}
