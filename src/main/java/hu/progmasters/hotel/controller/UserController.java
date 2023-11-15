package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.request.UserRegistrationForm;
import hu.progmasters.hotel.dto.response.UserInfo;
import hu.progmasters.hotel.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping()
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserInfo> registrationUser(@RequestBody @Valid UserRegistrationForm userRegistrationForm) {
        log.info("HTTP request, Post registration, body: " + userRegistrationForm.toString());
        UserInfo userInfo = userService.registrationUser(userRegistrationForm);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<String> userLoginn() {
        log.info("HTTP request, Get login");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity<String> result = ResponseEntity.ok("Login page content. You are logged is: " + authentication.isAuthenticated() + ". Your role is "
                + authentication.getAuthorities().toString());
        return result;
    }

    @GetMapping("/index")
    public String userLogin(){
        log.info("HTTP request, Get /index");
        return "index";
    }
}
