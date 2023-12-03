package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.domain.Hotel;
import hu.progmasters.hotel.dto.response.HotelGeocodingResponse;
import hu.progmasters.hotel.repository.HotelRepository;
import hu.progmasters.hotel.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
@Slf4j
public class ViewController {
    private final HotelRepository hotelRepository;


    public ViewController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @GetMapping("/showHotelsOnMap")
    public String showHotelsOnMap(Model model) {
        List<Hotel> hotels = hotelRepository.findAll();
        model.addAttribute("hotelData", hotels);
        log.info("HTTP GET request to /showHotelsOnMap");
        return "hotelmap";
    }
}

