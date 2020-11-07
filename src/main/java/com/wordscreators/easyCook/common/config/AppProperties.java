package com.wordscreators.easyCook.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    @Getter
    private final Auth auth = new Auth();

    @Getter
    private final OAuth2 oAuth2 = new OAuth2();

    @Data
    public static class Auth {
        private String tokenSecret;
        private Long tokenExpirationMs;
    }

    public static final class OAuth2 {
        @Getter
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public OAuth2 getAuthorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }
}
