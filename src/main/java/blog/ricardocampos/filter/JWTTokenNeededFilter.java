package blog.ricardocampos.filter;

import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.util.logging.Logger;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    KeyGenerator keyGenerator;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        // Get the HTTP Authorization header from the request
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            // Validate the token
            Key key = keyGenerator.generateKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            logger.info("#### valid token : " + token);
        } catch (Exception e) {
            logger.severe("#### invalid token : " + token);
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
