package hu.progmasters.hotel.dto.response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class ReservationDetails {

    private Long id;
    private String guestName;
    private LocalDate startDate;
    private LocalDate endDate;

}
