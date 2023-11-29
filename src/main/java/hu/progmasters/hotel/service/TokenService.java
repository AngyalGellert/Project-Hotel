package hu.progmasters.hotel.service;


import hu.progmasters.hotel.domain.Token;
import hu.progmasters.hotel.domain.User;
import hu.progmasters.hotel.exception.TokenException;
import hu.progmasters.hotel.repository.TokenRepository;
import hu.progmasters.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class TokenService {

    private final TokenRepository tokenRepository;
    private final EmailSenderService emailSenderService;

    private final int EXPIRATION_TIME_IN_MINUTE = 1;
    private final UserRepository userRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository,
                        EmailSenderService emailSenderService, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.emailSenderService = emailSenderService;
        this.userRepository = userRepository;
    }

    public Token generateToken(User user){
        Token token = new Token();
        token.setUser(user);
        UUID uuidToken = UUID.randomUUID();
        token.setToken(uuidToken.toString());
        token.setExpirationDate(LocalDateTime.now().plusMinutes(EXPIRATION_TIME_IN_MINUTE));
        tokenRepository.save(token);
        Token newToken = tokenRepository.findByToken(uuidToken.toString());
        return newToken;
    }


    public void activationUser(String activationToken) {
        Token token = tokenRepository.findByToken(activationToken);
        if(token == null){
            throw new TokenException("Not valid this activation token: "+ activationToken);
        }
        if(token.getExpirationDate().isBefore(LocalDateTime.now())){
             throw  new TokenException("This activation token is expired. Please request a password reminder.");
        }
        if(token.getUser().isEnable()){
            throw  new TokenException("This token has already been used");
        }
        token.getUser().setEnable(true);
        emailSenderService.sendConfirmedRegistrationEmail(token.getUser());
        tokenRepository.save(token);
    }

    public void newActivationTokenRequest(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException(email);
        }
        Token token = user.getToken();
        token.setExpirationDate(LocalDateTime.now());
        token.setToken(UUID.randomUUID().toString());
        tokenRepository.save(token);
        String link = "localhost:8080/token/"+ token.getToken();
//        emailSenderService.sendEmail(user, link);
        System.out.println(link);
    }
}
