package tn.esprit.projetsalledemarche.Service.ser.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmationEmail(String to, String username) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Confirmation de votre inscription à notre salle de marché 🎉");

            // Contenu personnalisé de l'email avec des emojis
            String emailContent = "Bonjour cher(e) trader " + username + " 👋,\n\n" +
                    "Nous sommes ravis de vous accueillir dans notre salle de marché 🚀 !\n\n" +
                    "Félicitations pour votre inscription 🎉. Nous sommes heureux de vous compter parmi nos membres et nous espérons que vous prendrez plaisir à explorer l'univers du trading 📈.\n\n" +
                    "Notre objectif est de vous aider à devenir un expert en trading 💼. Nous vous proposons diverses sessions de formation et des événements passionnants 🎓 pour vous permettre de développer vos compétences et maximiser vos opportunités de gains 💰.\n\n" +
                    "Nous vous souhaitons beaucoup de succès et de plaisir dans votre parcours au sein de notre communauté de traders 💪 !\n\n" +
                    "Cordialement,\nL'équipe de la salle de marché 🤝";

            message.setText(emailContent);
            mailSender.send(message);
            System.out.println("Email envoyé avec succès à : " + to);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }
}
