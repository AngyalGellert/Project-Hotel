package hu.progmasters.hotel.service;


import hu.progmasters.hotel.config.Role;
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

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserInfo registrationUser(UserRegistrationForm userRegistrationForm){
        if(findUserByEmail(userRegistrationForm.getEmail())){
           throw new EmailAlreadyInUseException(userRegistrationForm.getEmail());
        }
        User newUser= new User();
        newUser.setUserName(userRegistrationForm.getUserName());
        newUser.setEmail(userRegistrationForm.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRegistrationForm.getPassword()));
        newUser.setRole(Role.USER);
        userRepository.save(newUser);
        return modelMapper.map(newUser, UserInfo.class);
    }

    private boolean findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user != null){
            return true;
        }
        return false;
    }


    @Override
    public UserDetails loadUserByUsername(String getEmailAddressFromLoginPage) throws UsernameNotFoundException {
        User usersSearchByEmail = userRepository.findByEmail(getEmailAddressFromLoginPage);

        if (usersSearchByEmail == null) {

            throw new UsernameNotFoundException(getEmailAddressFromLoginPage);
        }

        return new UserLogin(usersSearchByEmail);
    }
}
