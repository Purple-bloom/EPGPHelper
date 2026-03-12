package custom.cyd.EpgpHelperBackend.Controller;

import custom.cyd.EpgpHelperBackend.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    AuthService authService;

    @PostMapping(
            value = "/login",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<String> login(@RequestBody String[] authDetails){
        String username = authDetails[0];
        String password = authDetails[1];

        logger.info("Login request received for username " + username);
        String token = authService.login(username, password);
        return ResponseEntity.ok().body(token);
    }
}
