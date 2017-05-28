package file;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReaderTest {

    private static final String SUBJECT = "This is the subject line!";
    private static final String MESSAGE_TEXT = "Hello,\r\n" +
            "\r\n" +
            "this is an example mail text.\r\n" +
            "I hope you enjoy working with automail!\r\n" +
            "\r\n" +
            "Greetings\r\n" +
            "The Author";
    private static final String WRONG_PATH_TO_FILE = "src/test/resources/nonexistent_file.txt";
    private static final List<String> RECIPIENTS = Arrays.asList(
            "madoka@magica.org", "homura@magica.jp", "sayaka@magica.net", "kyouko@msn.com", "mami@hotmail.de"
    );

    @Test
    void readSimpleFileTest() throws IOException {
        assertThat(Reader.readSimpleFile(Paths.get("src/test/resources/subject.txt"))).isEqualTo(SUBJECT);
        assertThat(Reader.readSimpleFile(Paths.get("src/test/resources/text.txt"))).isEqualTo(MESSAGE_TEXT);
    }

    @Test
    void readSimpleFileTestWithWrongPath() {
        Assertions.assertThrows(IOException.class,
                () -> Reader.readSimpleFile(Paths.get(WRONG_PATH_TO_FILE)));
    }

    @Test
    void readRecipientsTest() throws IOException {
        val recipients = Reader.readRecipients(Paths.get("src/test/resources/recipients.txt"));
        assertThat(recipients.length).isEqualTo(RECIPIENTS.size());
        for (val recipient : recipients) {
            assertThat(recipient.toString()).isIn(RECIPIENTS);
        }
    }

    @Test
    void readRecipientsTestW() {
        Assertions.assertThrows(IOException.class,
                () -> Reader.readRecipients(Paths.get(WRONG_PATH_TO_FILE)));
    }

    // TODO Investigate why the Exception is not thrown.
//    @Test
    void readFaultyRecipientsTest() {
        Assertions.assertThrows(RuntimeException.class,
                () -> Reader.readRecipients(Paths.get("src/test/resources/faulty_recipients.txt")));
    }

}