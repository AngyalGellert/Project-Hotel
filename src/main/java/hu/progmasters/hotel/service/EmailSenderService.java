package hu.progmasters.hotel.service;

import hu.progmasters.hotel.domain.User;
import hu.progmasters.hotel.dto.request.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    private final String REGISTRATION_SUBJECT = "Hotel Middle-earth: Welcome!";
    private final String EMAIL_CONFIRMATION = "Email confirmation reuqest";


    public void sendEmail(UserRegistrationForm request, String link) {
        String toEmail = request.getEmail();
        String body = "Dear " + request.getUserName() + "\n" +
                "\n" +
                "\n" +
                "In order to complete your registration, please click the link below!" + " \n" +
                link + " \n" +
                "Thank you!" + "\n" +
                "\n" +
                "\n" +
                "Kind regards," + "\n" +
                "Team Hotel Middle-earth";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(toEmail);
        email.setSubject(EMAIL_CONFIRMATION);
        email.setText(body);

        mailSender.send(email);
    }

    public void sendEmail(User user, String link) {
        String toEmail = user.getEmail();
        String body = "Dear " + user.getUserName() + "\n" +
                "\n" +
                "\n" +
                "This is new token for new passworn requets. Please click on the link: " + " \n" +
                link + " \n" +
                "Thank you!" + "\n" +
                "\n" +
                "\n" +
                "Kind regards," + "\n" +
                "Team Hotel Middle-earth";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(toEmail);
        email.setSubject(EMAIL_CONFIRMATION);
        email.setText(body);

        mailSender.send(email);
    }


    public void sendEmail(UserRegistrationForm request,
                          byte[] attachment,
                          String attachmentName) throws MessagingException, MailSendException {
        String toEmail = request.getEmail();
        String body = "Dear " + request.getUserName() + "\n" +
                "\n" +
                "\n" +
                "Thank you for your registration!" + " \n" +
                "In the attached file, you can find your user details. Please check them and let us know if any modification is needed." + " \n" +
                "Thank you!" + "\n" +
                "\n" +
                "\n" +
                "Kind regards," + "\n" +
                "Team Hotel Middle-earth";

        MimeMessage email = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(email, true, StandardCharsets.UTF_8.name());

        helper.setTo(toEmail);
        helper.setSubject(REGISTRATION_SUBJECT);
        helper.setText(body);
        helper.addAttachment(attachmentName, new ByteArrayResource(attachment));

        mailSender.send(email);
    }

//    public void sendEmail(ReservationRequest request,
//                          byte[] attachment,
//                          String attachmentName) throws MessagingException, MailSendException {
//
//        MimeMessage email = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(email, true, StandardCharsets.UTF_8.name());
//
//        helper.setTo(request.getToEmail());
//        helper.setSubject(request.getGuestName() + "'s reservation info");
//        helper.setText(request.getBody());
//        helper.addAttachment(attachmentName, new ByteArrayResource(attachment));
//
//        mailSender.send(email);
//        String currentTime = new Timestamp(System.currentTimeMillis()).toString();
//        return new EmailResponse(email.getSubject(), currentTime);
//    }





}
