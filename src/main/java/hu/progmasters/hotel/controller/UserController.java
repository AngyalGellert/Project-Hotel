package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.request.RoomForm;
import hu.progmasters.hotel.dto.request.UserRegistrationForm;
import hu.progmasters.hotel.dto.response.UserInfo;
import hu.progmasters.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserInfo> registrationUser(@RequestBody @Valid UserRegistrationForm userRegistrationForm){
        UserInfo userInfo = userService.registrationUser(userRegistrationForm);
        return new ResponseEntity(userInfo, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity







}
