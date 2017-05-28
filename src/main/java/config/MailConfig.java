package config;

import enums.MailProvider;
import lombok.Data;

@Data
public class MailConfig {

    private final String username;
    private final String password;
    private final MailProvider mailProvider;

}
