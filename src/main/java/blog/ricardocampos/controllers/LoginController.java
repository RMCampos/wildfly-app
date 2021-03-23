package blog.ricardocampos.controllers;

import blog.ricardocampos.repository.service.AuthService;
import blog.ricardocampos.security.User;
import blog.ricardocampos.vo.UserLogin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.KeyGenerator;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

@Path("/do-login")
@LocalBean
@Stateless
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @Context
    UriInfo uriInfo;

    @Inject
    KeyGenerator keyGenerator;

    @Inject
    AuthService authService;

    @POST
    @Path("/try")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserLogin userLogin) {
        try {
            logger.info("#### email/password : " + userLogin.getEmail() + "/" + userLogin.getPassword());

            // Authenticate the user using the credentials provided
            authenticate(userLogin.getEmail(), userLogin.getPassword());

            // Issue a token for the user
            String token = issueToken(userLogin.getEmail());

            // Return the token on the response
            return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
        } catch (Exception e) {
            String mensagemErro = "Usuário ou senha inválidos!";
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"mensagem\": \"" + mensagemErro + "\"}")
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
    }

    private void authenticate(String email, String password) throws Exception {
        User user = null;

        if (email.equals("ricardo@ricardocampos.blog")) {
            if (password.equals("123456")) {
                user = new User();
                user.setEmail("ricardo@ricardocampos.blog");
                user.setNome("Ricardo Campos");
            }
        }
        /*
        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_LOGIN_PASSWORD, User.class);
        query.setParameter("login", login);
        query.setParameter("password", PasswordUtils.digestPassword(password));
        User user = query.getSingleResult();
         */

        if (user == null)
            throw new SecurityException("Invalid user/password");
    }

    private String issueToken(String email) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(email)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        logger.info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
