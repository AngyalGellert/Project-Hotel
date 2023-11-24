package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.response.ResponseToken;
import hu.progmasters.hotel.dto.response.RoomDetails;
import hu.progmasters.hotel.service.OauthTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/paypal")
public class PaypalController {

    private final OauthTokenService oauthTokenService;

    @Autowired
    public PaypalController(OauthTokenService oauthTokenService) {
        this.oauthTokenService = oauthTokenService;
    }

    @PostMapping
    public ResponseEntity<ResponseToken> token() {
        log.info("Http request, Get paypal token");
        return new ResponseEntity<>( oauthTokenService.requestToken(), HttpStatus.OK);
    }


}
