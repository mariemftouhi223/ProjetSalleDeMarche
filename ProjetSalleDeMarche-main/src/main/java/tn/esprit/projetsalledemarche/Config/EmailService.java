package tn.esprit.projetsalledemarche.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (MailAuthenticationException e) {
            // Gérer l'erreur d'authentification
            throw new RuntimeException("Erreur d'authentification lors de l'envoi de l'e-mail", e);
        } catch (Exception e) {
            // Gérer d'autres exceptions
            throw new RuntimeException("Erreur lors de l'envoi de l'e-mail", e);
        }
    }}

