package br.com.chamadosprefrec.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import br.com.chamadosprefrec.handler.BusinessException;
import br.com.chamadosprefrec.model.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;



@Service
public class TokenService {
    @Value("${api.securty.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            
            // Chame o método generateExpirationDate para definir a data de expiração

            String token = JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(Date.from(this.generateExpirationDate())) // Converte Instant para Date
                    .sign(algorithm);

            return token;

        } catch (JWTCreationException exception) {
            throw new BusinessException("Erro na criação do JWT", exception);
        }
    }
    // Validação do Token
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return null;
        }
    }
    // Expiração do Token
    private Instant generateExpirationDate() {
        // Adiciona 2 horas à data/hora atual e converte para Instant
        return LocalDateTime.now()
                .plusHours(2) // Adiciona 2 horas
                .toInstant(ZoneOffset.ofHours(-3)); // Converte para Instant com o fuso horário UTC-3
    }
}
