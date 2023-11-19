package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/token")
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/{token}")
    public ResponseEntity<String> activationToken(@PathVariable("token") String token) {
        log.info("Http request, Get /api/reservation/{token} with variable: " + token);
        tokenService.activationUser(token);
        return new ResponseEntity<>("activation this token: " + token , HttpStatus.OK);
    }

    @GetMapping("/newtoken/{userEmail}")
    public ResponseEntity<String> newActivationTokenRequest(@PathVariable("userEmail") String email) {
        log.info("Http request, Get /api/reservation/newpass/{userEmail} with variable: " + email);
        tokenService.newActivationTokenRequest(email);
        return new ResponseEntity<>("Send new activation token to this email address " + email , HttpStatus.OK);
    }


}
