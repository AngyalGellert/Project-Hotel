package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.response.HotelGeocodingResponse;
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
    private final HotelService hotelService;


    public ViewController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/showHotelsOnMap")
    public String showHotelsOnMap(Model model) {
        List<HotelGeocodingResponse> hotels = hotelService.getHotelForMap();
        model.addAttribute("hotelData", hotels);
//        String message = "Hello from backend!";
//        model.addAttribute("message", message);
        return "hotelmap";
    }
}

