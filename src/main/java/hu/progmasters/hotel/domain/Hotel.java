package hu.progmasters.hotel.domain;

import hu.progmasters.hotel.dto.request.HotelCreateRequest;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(unique = true)
    private String name;

    @NotNull
    @Size(min = 1, max = 200)
    private String zipCode;

    @NotNull
    @Size(min = 1, max = 200)
    private String city;

    @NotNull
    @Size(min = 1, max = 200)
    private String address;

    @OneToMany (mappedBy = "hotel")
    private List <Room> roomList;

    @ElementCollection
    private List<String> imageUrls = new ArrayList<>();

}
