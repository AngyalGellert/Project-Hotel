package hu.progmasters.hotel.service;


import hu.progmasters.hotel.config.Role;
import hu.progmasters.hotel.domain.Token;
import hu.progmasters.hotel.domain.User;
import hu.progmasters.hotel.dto.request.UserRegistrationForm;
import hu.progmasters.hotel.dto.response.UserInfo;
import hu.progmasters.hotel.exception.EmailAlreadyInUseException;
import hu.progmasters.hotel.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper,
                       PasswordEncoder passwordEncoder, TokenService tokenService,
                       EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.emailSenderService = emailSenderService;
    }

    public UserInfo registrationUser(UserRegistrationForm userRegistrationForm) {
        if (findUserByEmail(userRegistrationForm.getEmail())) {
            throw new EmailAlreadyInUseException(userRegistrationForm.getEmail());
        }
        User newUser = new User();
        newUser.setUserName(userRegistrationForm.getUserName());
        newUser.setEmail(userRegistrationForm.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRegistrationForm.getPassword()));
        newUser.setRole(Role.ROLE_USER);
        userRepository.save(newUser);
        Token token = tokenService.generateToken(newUser);
        newUser.setToken(token);
        userRepository.save(newUser);
        String activationLink = "localhost:8080/token/" + token.getToken();
        System.out.println(activationLink);
        emailSenderService.sendEmail(userRegistrationForm);
        return modelMapper.map(newUser, UserInfo.class);
    }

    private boolean findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }


    @Override
    public UserDetails loadUserByUsername(String getEmailAddressFromLoginPage) throws UsernameNotFoundException {
        User usersSearchByEmail = userRepository.findByEmail(getEmailAddressFromLoginPage);
        Token token = usersSearchByEmail.getToken();
        if (usersSearchByEmail == null) {

            throw new UsernameNotFoundException(getEmailAddressFromLoginPage);
        }

        return new UserLogin(usersSearchByEmail);
    }

    public User findUserById(Long userID) {
        Optional<User> result = userRepository.findById(userID);
        return result.orElseThrow(() -> new UsernameNotFoundException("User was not found"));
    }

    public void userActivation(User user){
        user.setEnable(true);
        userRepository.save(user);
    }


}
