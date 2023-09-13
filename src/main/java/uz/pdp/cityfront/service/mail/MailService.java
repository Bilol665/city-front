package uz.pdp.cityfront.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;
    public void sendResetPassword(String email) {
        String message = "Hi there, this message was sent to reset your password, if you did not requested it just ignore it!" +
                "\nHere is the link to reset it: http://localhost:8085/password-reset/" + email + "/1";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Reset");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);
    }
}
