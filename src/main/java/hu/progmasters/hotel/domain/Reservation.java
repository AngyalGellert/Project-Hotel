package hu.progmasters.hotel.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    private String guestName;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @ManyToOne
    private Room room;

    private boolean deleted;

}
