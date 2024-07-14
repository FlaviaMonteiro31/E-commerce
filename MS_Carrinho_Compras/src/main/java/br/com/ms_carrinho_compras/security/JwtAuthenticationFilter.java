package br.com.ms_carrinho_compras.security;

import br.com.ms_carrinho_compras.integration.gateway.ConsultaUsuarioGateway;
import br.com.ms_carrinho_compras.model.records.ConsultaUsuarioRequest;
import br.com.ms_carrinho_compras.model.records.ConsultaUsuarioResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Value("${api.security.token.secret}")
    private String secret;

    @Autowired
    private ConsultaUsuarioGateway usuarioGateway;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = this.recoverToken(request);
        String idUsuario = validateToken(token);
        System.out.println("O idUsuario do token: " + idUsuario);

        if(isValidUUID(idUsuario)){

            ConsultaUsuarioResponse cliente = usuarioGateway.consultaUsuario(new GenericMessage<>(new ConsultaUsuarioRequest(UUID.fromString(idUsuario))));

            if (cliente != null){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(idUsuario, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        filterChain.doFilter(request, response);

    }

    private String recoverToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

    private String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception){
            return "";
        }
    }

    public static boolean isValidUUID(String uuidString) {
        String regex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        return Pattern.matches(regex, uuidString);
    }



}
