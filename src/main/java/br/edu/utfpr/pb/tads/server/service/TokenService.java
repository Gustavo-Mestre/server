package br.edu.utfpr.pb.tads.server.service;

import br.edu.utfpr.pb.tads.server.model.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;


//Informa o spring que é uma classe de serviço.
@Service
public class TokenService {

    @Value("${api.security.token.secret}") //Pega os valores das variáveis de ambiente para gerar uma secret.
    private String secret;

    //Método para gerar token.
    public String generateToken(UserModel userModel){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret); //Algoritmo para gerar token advindo da dependência do JWT.
                                                            // O parâmetro é uma SECRET, que tem o objetivo de tornar o sistema mais seguro,
                                                            // pois garante que os tokens só possam ser verificados e validados com a posse dessa chave secreta.

            String token = JWT.create()
                    .withIssuer("auth-server-api")
                    .withSubject(userModel.getName())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            return token;

            //withIssuer --> quem criou;
            //withSubject -- User que está recebendo este token.
            //withExpiresAt --> tempo de expiração.
            //sign --> "assinar o processo de geração e referência do token.
        }catch (JWTCreationException exception){
            throw new RuntimeException("Error while generating token", exception);
        }

    }

    //Método para verificar validade do token.
    public String validateToken(String token){
        try {
            //Faz o decode do token.
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-server-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception){
            return "";
        }
    }

    //Método para gerar um tempo de expiração do código.
    public Instant generateExpirationDate() {
        // Define o fuso horário
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");

        // Calcula a data/hora de expiração
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId).plusMonths(4);

        // Converte para Instant
        return zonedDateTime.toInstant();
    }

}
