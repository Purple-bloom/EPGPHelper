package custom.cyd.GuildHelper.Service;

import custom.cyd.GuildHelper.GuildHelperApplication;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Logger;
import java.util.random.RandomGenerator;

@Service
public class AuthService {
    Logger logger = Logger.getLogger(AuthService.class.getName());

    private final int random = Random.from(RandomGenerator.getDefault()).nextInt();

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
        char[] chars = (username.toLowerCase() + password).toCharArray();

        String token = "";
        for (char elem : chars){
            token += elem+random;
            token += elem*random;
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
