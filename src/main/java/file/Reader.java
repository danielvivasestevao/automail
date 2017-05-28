package file;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reader {

    public static String readSimpleFile(final Path pathToFile) throws IOException {
        return new String(Files.readAllBytes(pathToFile));
    }

    public static Address[] readRecipients(final Path pathToRecipientsFile) throws IOException {
        final Address[] addresses;
        final Long numberOfAddresses;
        try (Stream<String> stream = Files.lines(pathToRecipientsFile)) {
            numberOfAddresses = stream.count();
        }
        addresses = new Address[numberOfAddresses.intValue()];
        try (Stream<String> stream = Files.lines(pathToRecipientsFile)) {
            stream
                .map(line -> {
                    final InternetAddress address;
                    try {
                        address = new InternetAddress(line);
                    } catch (AddressException e) {
                        throw new RuntimeException("Invalid mail address in recipients file", e);
                    }
                    return address;
                })
                .collect(Collectors.toList())
                .toArray(addresses);
        }
        return addresses;
    }

}
