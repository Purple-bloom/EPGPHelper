package custom.cyd.GuildHelper;

import custom.cyd.GuildHelper.Service.AuthService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class GuildHelperApplication {
	static Logger logger = Logger.getLogger(GuildHelperApplication.class.getName());

	public static String username;
	public static String password;


	public static void main(String[] args) {
		for(String arg : args){
			String[] params = arg.replace("--", "").split("=");
			if(params[0].equalsIgnoreCase("username")){
				username = params[1];
				logger.info("Username set.");
			}
			if(params[0].equalsIgnoreCase("password")){
				password = params[1];
				logger.info("Password set.");
			}
		}
		SpringApplication.run(GuildHelperApplication.class, args);
	}

}
