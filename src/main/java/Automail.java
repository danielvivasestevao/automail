import config.MailConfig;
import config.MessageConfig;
import enums.MailProvider;
import file.Reader;
import lombok.val;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

// TODO v0.1: Whole project: add documentation
public class Automail {

    // Throw all Exceptions. All possible Exceptions occur due to programming errors,
    // so the application is not able to recover anyway.
    public static void main(String[] args) throws URISyntaxException, IOException, AddressException {
        checkArgs(args);
        val jarDirectory = getJarDirectory();
        val username = args[0];
        val password = args[1];
        val pathToAttachment = args[2];  // TODO v0.1: What if we want no attachment?
        val sender = new InternetAddress(args[3]);

        val messageConfig = new MessageConfig(
                sender,
                Reader.readRecipients(jarDirectory.resolve("recipients.txt")),
                Reader.readSimpleFile(jarDirectory.resolve("subject.txt")),
                Reader.readSimpleFile(jarDirectory.resolve("text.txt")),
                pathToAttachment
        );

        val mailConfig = new MailConfig(
                username,
                password,
                MailProvider.WEB_DE  // TODO v0.2: Allow user to choose MailProvider
        );

        val mailSender = new MailSender(messageConfig, mailConfig);

        try {
            mailSender.sendMail();
        } catch (InterruptedException e) {
            throw new RuntimeException("Waiting time between sending of mails was interrupted", e);
        }
    }

    private static void checkArgs(final String[] args) {
        if (args.length != 4) {
            throw new RuntimeException(
                    String.format("Wrong number of arguments. " +
                            "Expected 4, but found %d. " +
                            "Make sure that all arguments are properly encapsulated with quotes and all special characters are escaped with \\.",
                            args.length));
        }
    }

    private static Path getJarDirectory() throws URISyntaxException {
        return Paths.get(
                Automail.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI()
        ).getParent();
    }

}
