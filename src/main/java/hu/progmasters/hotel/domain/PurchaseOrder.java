package hu.progmasters.hotel.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter

public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<Reservation> basket;

    private boolean paid=false;

    private LocalDate paidDate = LocalDate.now();

    private double sumOrder;

    private String uniqueId;

    private String paypalId;


    public PurchaseOrder() {
        this.basket = new ArrayList<>();
    }
}
