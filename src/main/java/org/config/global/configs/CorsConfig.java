package org.config.global.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    //@Value("${cors.allowed}")
    //public List<String> allowedOrigin;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        //if (allowedOrigin == null || allowedOrigin.isEmpty()) {
            config.addAllowedOrigin("*");
        //} else {
        //    config.setAllowedOrigins(allowedOrigin);
            config.setAllowCredentials(true);
        //}

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
