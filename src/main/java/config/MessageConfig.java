package config;

import lombok.Data;

import javax.mail.Address;

@Data
public class MessageConfig {

    private final Address sender;
    private final Address[] recipients;
    private final String subject;
    private final String text;
    private final String pathToAttachment;

}
