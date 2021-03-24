package blog.ricardocampos.controller;

import blog.ricardocampos.repository.entity.UserEntity;
import blog.ricardocampos.repository.service.AuthService;
import blog.ricardocampos.security.JwtUtil;
import blog.ricardocampos.security.User;
import blog.ricardocampos.vo.UserLogin;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.util.logging.Logger;

@Path("/do-login")
@LocalBean
@Stateless
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @Context
    UriInfo uriInfo;

    @Inject
    AuthService authService;

    @Inject
    JwtUtil jwtUtil;

    @POST
    @Path("/try")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserLogin userLogin) {
        try {
            if (userLogin == null) {
                String mensagemErro = "No user credentials specified!";
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"" + mensagemErro + "\"}")
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .build();
            }

            logger.info("#### email/password : " + userLogin.getEmail() + "/" + userLogin.getPassword());

            // Authenticate the user using the credentials provided
            UserEntity userEntity = authenticate(userLogin.getEmail(), userLogin.getPassword());

            // Issue a token for the user
            String token = jwtUtil.createToken(
                    userEntity.getId().toString(),
                    userEntity.getEmail(),
                    userEntity.getName(),
                    800000L
            );

            if (token == null) {
                logger.info("#### no token!");
                String mensagemErro = "No token!";
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"" + mensagemErro + "\"}")
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .build();
            }

            // Return the token on the response
            return Response.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity("{user: " + userEntity + "}")
                    .build();
        } catch (Exception e) {
            String mensagemErro = "Usuário ou senha inválidos!";
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"" + mensagemErro + "\"}")
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
    }

    private UserEntity authenticate(String email, String password) throws Exception {
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

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("ricardo@ricardocampos.blog");
        userEntity.setName("Ricardo Campos");

        return userEntity;
    }
}
