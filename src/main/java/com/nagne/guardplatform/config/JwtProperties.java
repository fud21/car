package com.nagne.guardplatform.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secret;
    private long expiration;

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
