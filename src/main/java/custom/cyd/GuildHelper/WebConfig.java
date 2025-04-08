package custom.cyd.GuildHelper;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Adjust based on your API path
                .allowedOrigins("*")        // Allows all origins
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow the methods you need
                .allowedHeaders("*")        // Allow all headers
                .allowCredentials(false);
    }
}
