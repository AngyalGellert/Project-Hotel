package hu.progmasters.hotel.service;

import hu.progmasters.hotel.domain.Room;
import hu.progmasters.hotel.domain.User;
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
    private final RoomService roomService;

    private final String WELCOME_EMAIL_REGISTRATION = "Hotel Middle-earth: Welcome!";
    private final String CONFIRMATION_EMAIL = "Hotel Middle-earth: Confirm your e-mail";
    private final String PASSWORD_RESET = "Hotel Middle-earth: Reset your password";
    private final String RESERVATION = "Hotel Middle-earth: Reservation at ";


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

    public void sendConfirmedRegistrationEmail(User user) {
        String body = "Dear " + user.getUserName() + "\n" +
                "\n" +
                "\n" +
                "Thank you for confirming your e-mail address, the registration was successful!" + " \n" +
                "In the attached file, you can find your user details. Please check them and let us know if any modification is needed." + " \n" +
                "Thank you!" + "\n" +
                "\n" +
                "\n" +
                "Kind regards," + "\n" +
                "Team Hotel Middle-earth";

        byte[] attachment = generateAttachment(user);

        MimeMessage email = null;
        try {
            email = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(email, true, StandardCharsets.UTF_8.name());

            helper.setTo(user.getEmail());
            helper.setSubject(WELCOME_EMAIL_REGISTRATION);
            helper.setText(body);
            helper.addAttachment("User Information.pdf", new ByteArrayResource(attachment));
        } catch (MessagingException e) {
            throw new RuntimeException("Attachment failed");
        }

        mailSender.send(email);
    }

    public void sendPasswordResetEmail(User user, String link) {
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
        email.setTo(user.getEmail());
        email.setSubject(PASSWORD_RESET);
        email.setText(body);

        mailSender.send(email);
    }

    public void sendEmail(Room room, User user) {
        String body = "Dear " + user.getUserName() + "\n" +
                "\n" +
                "\n" +
                "Your reservation was successful. The attachment of this email contains the information of your reservation." +
                " \n" +
                " \n" +
                "Thank you!" + "\n" +
                "\n" +
                "\n" +
                "Kind regards," + "\n" +
                "Team Hotel Middle-earth";

        generateAttachmentForReservationRelatedEmail(room, user, body);
    }


    public void sendEmail(User user, Room room) {
        String body = "Dear " + user.getUserName() + "\n" +
                "\n" +
                "\n" +
                "Your reservation was modified successfully. The attachment of this email contains the information of your reservation." +
                " \n" +
                " \n" +
                "Thank you!" + "\n" +
                "\n" +
                "\n" +
                "Kind regards," + "\n" +
                "Team Hotel Middle-earth";

        generateAttachmentForReservationRelatedEmail(room, user, body);
    }

    public void sendReservationDeletingEmail(User user, Room room) {
        String body = "Dear " + user.getUserName() + "\n" +
                "\n" +
                "\n" +
                "Your reservation was deleted successfully. Feel free to place another reservation that fits your needs." +
                " \n" +
                " \n" +
                "Thank you!" + "\n" +
                "\n" +
                "\n" +
                "Kind regards," + "\n" +
                "Team Hotel Middle-earth";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject(RESERVATION + " " + room.getHotel().getName());
        email.setText(body);

        mailSender.send(email);
    }


    private byte[] generateAttachment(User user) {
        String title = "User Information for " + user.getUserName();
        String content = "Your email address is: " + user.getEmail();
        return pdfBoxService.generatePdf(title, content);
    }

    private byte[] generateAttachment(Room room, User user) {
        String title = "Reservation for " + user.getUserName();
        String content = "The room you've reserved is: " + room.getName();
        return pdfBoxService.generatePdf(title, content);
    }

    private void generateAttachmentForReservationRelatedEmail(Room room, User user, String body) {
        byte[] attachment = generateAttachment(room, user);

        MimeMessage email = null;
        try {
            email = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(email, true, StandardCharsets.UTF_8.name());

            helper.setTo(user.getEmail());
            helper.setSubject(RESERVATION + " " + room.getHotel().getName());
            helper.setText(body);
            helper.addAttachment("User Information.pdf", new ByteArrayResource(attachment));
        } catch (MessagingException e) {
            throw new RuntimeException("Attachment failed");
        }

        mailSender.send(email);
    }

}
