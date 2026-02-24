package custom.cyd.GuildHelper.Service;

import custom.cyd.GuildHelper.GuildHelperApplication;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Service
public class AuthService {
    Logger logger = Logger.getLogger(AuthService.class.getName());

    private String authToken = null;

    /**
     * @param username
     * @param password
     * @return String of auth token
     */
    public String login(String username, String password){
        if (username.equalsIgnoreCase(GuildHelperApplication.username) && password.equals(GuildHelperApplication.password)){
            logger.info("credentials valid, generating token");
            return generateToken(username, password);
        }
        return null;
    }

    public String generateToken(String username, String password){
        byte[] bytes = (username.toLowerCase()+password).getBytes(StandardCharsets.UTF_8);
        String token = "";
        for (byte elem : bytes){
            token += Byte.toString((byte) (elem*2.14));
        }
        this.authToken = token;
        return token;
    }

    public boolean isValid (String input){
        if (input.equals(authToken)) {
            logger.info("Token is valid.");
            return true;
        }
        logger.info("Invalid token supplied: " + input);
        return false;
    }
}
