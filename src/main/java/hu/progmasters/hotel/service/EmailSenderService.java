package hu.progmasters.hotel.service;

import hu.progmasters.hotel.domain.Reservation;
import hu.progmasters.hotel.domain.User;
import hu.progmasters.hotel.dto.request.ReservationRequest;
import hu.progmasters.hotel.dto.request.UserRegistrationForm;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;

@Service
@Transactional
@AllArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final PdfBoxService pdfBoxService;
    private final UserService userService;
    private final ReservationService reservationService;
    private final RoomService roomService;

    private final String WELCOME_EMAIL_REGISTRATION = "Hotel Middle-earth: Welcome!";
    private final String CONFIRMATION_EMAIL = "Hotel Middle-earth: Confirm your e-mail";

    private final String RESERVATION = "Hotel Middle-earth: Your reservation at ";


    public void sendRegistrationConfirmationEmail(UserRegistrationForm form, String link) {
        String body = "Dear " + form.getUserName() + "\n" +
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
        email.setTo(form.getEmail());
        email.setSubject(CONFIRMATION_EMAIL);
        email.setText(body);

        mailSender.send(email);
    }

    public void sendEmail(User user, String link) {
        String toEmail = user.getEmail();
        String body = "Dear " + user.getUserName() + "\n" +
                "\n" +
                "\n" +
                "This is your new token for the new password request. Please click on the following link: " + " \n" +
                link + " \n" +
                "Thank you!" + "\n" +
                "\n" +
                "\n" +
                "Kind regards," + "\n" +
                "Team Hotel Middle-earth";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(toEmail);
        email.setSubject(CONFIRMATION_EMAIL);
        email.setText(body);

        mailSender.send(email);
    }

//    public void sendEmail(UserRegistrationForm form) {
//        String body = "Dear " + form.getUserName() + "\n" +
//                "\n" +
//                "\n" +
//                "Thank you for confirming your e-mail address, the registration was successful!" + " \n" +
//                "In the attached file, you can find your user details. Please check them and let us know if any modification is needed." + " \n" +
//                "Thank you!" + "\n" +
//                "\n" +
//                "\n" +
//                "Kind regards," + "\n" +
//                "Team Hotel Middle-earth";
//
//        byte[] attachment = generateAttachment(form);
//
//        MimeMessage email = null;
//        try {
//            email = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(email, true, StandardCharsets.UTF_8.name());
//
//            helper.setTo(form.getEmail());
//            helper.setSubject(RESERVATION + form);
//            helper.setText(body);
//            helper.addAttachment("User Information.pdf", new ByteArrayResource(attachment));
//        } catch (MessagingException e) {
//            throw new RuntimeException("Attachment failed");
//        }
//
//        mailSender.send(email);
//    }

//    public void sendEmail(ReservationRequest form) {
//        String body = "Dear " + form.getRoomId() + "\n" +
//                "\n" +
//                "\n" +
//                "Thank you for confirming your e-mail address, the registration was successful!" + " \n" +
//                "In the attached file, you can find your user details. Please check them and let us know if any modification is needed." + " \n" +
//                "Thank you!" + "\n" +
//                "\n" +
//                "\n" +
//                "Kind regards," + "\n" +
//                "Team Hotel Middle-earth";
//
//        byte[] attachment = generateAttachment(form);
//
//        MimeMessage email = null;
//        try {
//            email = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(email, true, StandardCharsets.UTF_8.name());
//
//            helper.setTo(reservation.);
//            helper.setSubject(WELCOME_EMAIL_REGISTRATION);
//            helper.setText(body);
//            helper.addAttachment("User Information.pdf", new ByteArrayResource(attachment));
//        } catch (MessagingException e) {
//            throw new RuntimeException("Attachment failed");
//        }
//
//        mailSender.send(email);
//    }
//
//
//
//    private byte[] generateAttachment(UserRegistrationForm form) {
//        String title = "User Information for " + form.getUserName();
//        String content = "Your email address is: " + form.getEmail();
//        return pdfBoxService.generatePdf(title, content);
//    }
//
//    private byte[] generateAttachment(ReservationRequest form) {
//        String title = "Reservation for " + form.getRoomId();
//        String content = "Your email address is: " + form.getEmail();
//        return pdfBoxService.generatePdf(title, content);
//    }


}
