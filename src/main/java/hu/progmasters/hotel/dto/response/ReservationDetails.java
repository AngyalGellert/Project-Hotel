package hu.progmasters.hotel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDetails {

    private Long id;

    private String guestName;

    private LocalDate startDate;

    private LocalDate endDate;

}
