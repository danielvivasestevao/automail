package enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MailProviderTest {

    @Test
    void getMailProviderByString() {
        assertThat(MailProvider.valueOf("HOTMAIL")).isEqualTo(MailProvider.HOTMAIL);
        assertThat(MailProvider.valueOf("WEB_DE")).isEqualTo(MailProvider.WEB_DE);
    }

    @Test
    void getProvider() {
        assertThat(MailProvider.HOTMAIL.getProvider()).isEqualTo("hotmail");
        assertThat(MailProvider.WEB_DE.getProvider()).isEqualTo("web.de");
    }

    @Test
    void getPort() {
        assertThat(MailProvider.HOTMAIL.getPort()).isEqualTo(587);
        assertThat(MailProvider.WEB_DE.getPort()).isEqualTo(587);
    }

    @Test
    void getHost() {
        assertThat(MailProvider.HOTMAIL.getHost()).isEqualTo("smtp.live.com");
        assertThat(MailProvider.WEB_DE.getHost()).isEqualTo("smtp.web.de");
    }

}