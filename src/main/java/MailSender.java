import config.MailConfig;
import config.MessageConfig;
import lombok.AllArgsConstructor;
import lombok.val;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.nio.file.Paths;
import java.util.Properties;

@AllArgsConstructor
class MailSender {

    private final MessageConfig messageConfig;
    private final MailConfig mailConfig;

    void sendMail() throws InterruptedException {
        val session = getSession(false);

        try {
            val transport = getTransport(session);
            for (val recipient : messageConfig.getRecipients()) {
                try {
                    val message = buildMimeMessage(session, recipient);
                    try {
                        transport.sendMessage(message, new Address[]{recipient});
                        System.out.println(String.format("Email successfully sent to %s", recipient.toString()));
                    } catch (MessagingException mex) {
                        System.out.println((String.format("Could not send mail to %s", recipient.toString())));
                        mex.printStackTrace();
                    }                } catch (MessagingException mex) {
                    throw new RuntimeException("Could not build message. Are all parameters correct?", mex);
                }
                Sleeper.sleep(60);  // TODO v0.1: Do not call after sending mail to last recipient
            }
        } catch (MessagingException mex) {
            throw new RuntimeException("Could not establish transport", mex);
        }
    }

    private Session getSession(final boolean debugMode) {
        val session = Session.getDefaultInstance(getProperties());
        session.setDebug(debugMode);
        return session;
    }

    private Properties getProperties() {
        val properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", mailConfig.getMailProvider().getHost());
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");
        return properties;
    }

    private Transport getTransport(Session session) throws MessagingException {
        val transport = session.getTransport("smtp");
        transport.connect(
                mailConfig.getMailProvider().getHost(),
                mailConfig.getMailProvider().getPort(),
                mailConfig.getUsername(),
                mailConfig.getPassword());
        return transport;
    }

    private MimeMessage buildMimeMessage(final Session session, final Address recipient)
            throws MessagingException {
        val message = new MimeMessage(session);
        message.setFrom(messageConfig.getSender());
        message.addRecipient(Message.RecipientType.TO, recipient);
        message.setSubject(messageConfig.getSubject());

        val mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(buildMessageContentBodyPart(messageConfig.getText()));
        mimeMultipart.addBodyPart(getAttachment());
        message.setContent(mimeMultipart);

        return message;
    }

    private MimeBodyPart buildMessageContentBodyPart(final String messageContent) throws MessagingException {
        val mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setText(messageContent);
        return mimeBodyPart;
    }

    private MimeBodyPart getAttachment() throws MessagingException {
        val mimeBodyPart = new MimeBodyPart();
        val dataSource = new FileDataSource(messageConfig.getPathToAttachment());
        mimeBodyPart.setDataHandler(new DataHandler(dataSource));
        mimeBodyPart.setFileName(Paths.get(messageConfig.getPathToAttachment()).getFileName().toString());
        return mimeBodyPart;
    }

}
