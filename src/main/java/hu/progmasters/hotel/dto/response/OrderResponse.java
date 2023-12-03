package hu.progmasters.hotel.dto.response;

import hu.progmasters.hotel.domain.Reservation;
import hu.progmasters.hotel.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class OrderResponse {

    private Long id;

    private String userEmail;

    private List<Reservation> basket;

    private boolean paid = false;

    private LocalDate paidDate = LocalDate.now();

    private double sumOrder;

}
