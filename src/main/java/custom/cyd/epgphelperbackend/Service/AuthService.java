package custom.cyd.epgphelperbackend.Service;

import custom.cyd.epgphelperbackend.EpgpHelperBackend;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.logging.Logger;
import java.util.random.RandomGenerator;

@Service
public class AuthService {
    Logger logger = Logger.getLogger(AuthService.class.getName());

    private final int random = Random.from(RandomGenerator.getDefault()).nextInt(5)+2;

    private String authToken = null;

    /**
     * @param username
     * @param password
     * @return String of auth token
     */
    public String login(String username, String password){
        if (username.equalsIgnoreCase(EpgpHelperBackend.username) && password.equals(EpgpHelperBackend.password)){
            logger.info("credentials valid, generating token");
            return generateToken(username, password);
        }
        return null;
    }

    public String generateToken(String username, String password){
        char[] chars = (username.toLowerCase() + password).toCharArray();

        String token = "";
        for (char elem : chars){
            token += elem*random%10;
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
