package blog.ricardocampos.controllers;

import blog.ricardocampos.repository.service.AuthService;
import blog.ricardocampos.security.AuthenticationResponse;
import blog.ricardocampos.security.LoginCredentials;
import blog.ricardocampos.security.MyToken;
import blog.ricardocampos.security.User;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

@Path("/")
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @Context
    HttpServletRequest httpServletRequest;

    @Inject
    AuthService authService;

    @POST
    @Path("/try")
    public Response login(LoginCredentials credentials) {
        String email = "";
        String password = "";

        User user = MyToken.getUser(httpServletRequest);
        if (user == null) {
            email = credentials.getEmail();
            password = credentials.getPassword();
        }

        if (email.equals("ricardo@ricardocampos.blog")) {
            if (password.equals("123456")) {
                user = new User();
                user.setEmail("ricardo@ricardocampos.blog");
                user.setNome("Ricardo Campos");
            }
        }

        if (user != null) {
            AuthenticationResponse authenticationResponse = new AuthenticationResponse(user);
            return Response.ok().cookie(MyToken.criarTokenCookie(httpServletRequest, user))
                    .entity(authenticationResponse).type(MediaType.APPLICATION_JSON_TYPE).build();
        }

        String mensagemErro = "Usuário ou senha inválidos!";

        return Response.status(Response.Status.UNAUTHORIZED).entity("{\"mensagem\": \"" + mensagemErro + "\"}")
                .type(MediaType.APPLICATION_JSON_TYPE).build();

    }

    @POST
    @Path("/logout")
    public Response logout() {
        NewCookie[] newCookies = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            newCookies = new NewCookie[cookies.length];
            LocalDateTime localDateTime = LocalDateTime.now().minusMonths(7);
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

            for (int i=0; i<cookies.length; i++) {
                Cookie cookie = cookies[i];
                newCookies[i] = new NewCookie(
                        cookie.getName(),
                        cookie.getValue(),
                        httpServletRequest.getContextPath(),
                        null,
                        cookie.getVersion(),
                        cookie.getComment(),
                        0,
                        Date.from(instant),
                        cookie.getSecure(),
                        cookie.isHttpOnly()
                );
            }
        }

        return Response.ok().cookie(newCookies).build();
    }
}
