package com.oluwasayo.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtUtil {

    private final JwtEncoder jwtEncoder;

    public JwtUtil(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(String username) {
        Instant now = Instant.now();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .subject(username)
                .issuedAt(now)
                .expiresAt(now.plus(15, ChronoUnit.MINUTES))
                .build();

        JwsHeader header = JwsHeader.with(() -> "HS256").build();
        return jwtEncoder.encode(
                JwtEncoderParameters.from(header, jwtClaimsSet)
        ).getTokenValue();
    }

}
