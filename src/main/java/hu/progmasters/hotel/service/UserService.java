package hu.progmasters.hotel.service;


import hu.progmasters.hotel.config.Role;
import hu.progmasters.hotel.domain.User;
import hu.progmasters.hotel.dto.request.UserRegistrationForm;
import hu.progmasters.hotel.dto.response.UserInfo;
import hu.progmasters.hotel.exception.EmailAlreadyInUseException;
import hu.progmasters.hotel.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserInfo registrationUser(UserRegistrationForm userRegistrationForm){
        if(findUserByEmail(userRegistrationForm.getEmail())){
           throw new EmailAlreadyInUseException(userRegistrationForm.getEmail());
        }
        User newUser= new User();
        newUser.setUserName(userRegistrationForm.getUserName());
        newUser.setEmail(userRegistrationForm.getEmail());
        newUser.setPassword(userRegistrationForm.getPassword());
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


}
