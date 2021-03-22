package blog.ricardocampos.controllers;

import blog.ricardocampos.repository.service.AuthService;
import blog.ricardocampos.repository.service.UserService;
import blog.ricardocampos.security.AuthenticationResponse;
import blog.ricardocampos.security.LoginCredentials;
import blog.ricardocampos.security.MyToken;
import blog.ricardocampos.security.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
}
