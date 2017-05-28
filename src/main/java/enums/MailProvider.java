package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO v0.2: Add more popular mail providers
@AllArgsConstructor
public enum MailProvider {
    HOTMAIL ("hotmail", 587, "smtp.live.com"),
    WEB_DE ("web.de", 587, "smtp.web.de");

    @Getter private String provider;
    @Getter private int port;
    @Getter private String host;

}
